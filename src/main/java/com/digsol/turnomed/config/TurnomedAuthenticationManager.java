package com.digsol.turnomed.config;

import com.digsol.turnomed.mapper.AddressesMapper;
import com.digsol.turnomed.mapper.DoctorMapper;
import com.digsol.turnomed.mapper.UsersMapper;
import com.digsol.turnomed.model.Address;
import com.digsol.turnomed.model.Doctor;
import com.digsol.turnomed.model.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.*;

@Service

public class TurnomedAuthenticationManager implements AuthenticationManager {
    private static final Logger log = LoggerFactory.getLogger(TurnomedAuthenticationManager.class);

    public static final MessageDigestPasswordEncoder ENCODER = new MessageDigestPasswordEncoder("SHA-1");
    public static final String SALT = "__123__321__";

    private UsersMapper usersMapper;

    private DoctorMapper doctorMapper;

    private AddressesMapper addressesMapper;
    private Map<String, Users> defaultUsers = new HashMap<>();

    public TurnomedAuthenticationManager(UsersMapper usersMapper, DoctorMapper doctorMapper, AddressesMapper addressesMapper) {
        this.usersMapper = usersMapper;
        this.doctorMapper = doctorMapper;
        this.addressesMapper = addressesMapper;
        defaultUsers.put("admin", new Users(-1, "admin", "admin", "admin",
                "admin@admin.com", "{UUjK0wvwrT0BImk9/8cgSi/+UauFDPcUkkpHFri4qFA=}01fda4c2e40ea0a6d1451d5ccf6c9195f20dd967"));
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        Users users = defaultUsers.get(name);
        if (users == null) {
            users = usersMapper.getUserByEmail(name);
        }
        if (checkConfirmRegistration(users)) {
            boolean valid = ENCODER.matches(password.concat(SALT), users.getPassword());
            if (valid) {
                Set<GrantedAuthority> grants = checkRoles(users);
                log.debug("GrantedAuthority {}", grants);
                Users NewUser = new Users(users, grants);
                return new UsernamePasswordAuthenticationToken(NewUser, NewUser.getPassword(), grants);
            }
            throw new BadCredentialsException("Wrong password");
        }
        throw new BadCredentialsException("User with this login does not exist");
    }

    private Set<GrantedAuthority> checkRoles(Users users) {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        Address address = addressesMapper.getAddressInformationByEmail(users);
        Doctor doctor = doctorMapper.selectByPrimaryKey(users.getId());
        if (Objects.nonNull(doctor)) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_TRAINER"));
        }
        if (Objects.nonNull(address)) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_SPORTSCOMPL"));
        }
        return grantedAuthorities;
    }

    /**
     * Check if registration is confirmed by user
     * @param users
     * @return true, if user exists and confirmed their email.
     */
    private Boolean checkConfirmRegistration(Users users) {
        if (Objects.nonNull(users)) {
            if (users.getRegStatus() == 0) {
                return true;
            }
            throw new BadCredentialsException("Registration is not confirmed! A letter was sent to your email "
                    + users.getEmail());
        }
        return false;
    }

        public static void main (String[]args){
            System.out.println(ENCODER.encode("0000".concat(SALT)));
        }

    }
