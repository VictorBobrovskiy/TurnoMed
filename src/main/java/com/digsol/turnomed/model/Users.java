package com.digsol.turnomed.model;

import com.digsol.turnomed.dto.UsersDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Getter
@Setter
public class Users extends User {

    private static final long serialVersionUID = -8136811209625422423L;

    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private Integer regStatus;

    private Timestamp regDate;

    private Timestamp confirmDate;

    public Users(UsersDTO usersDTO) {
        super(usersDTO.getEmail(), usersDTO.getPassword(), Collections.emptyList());
        this.firstName = usersDTO.getFirstName();
        this.lastName = usersDTO.getLastName();
        this.email = usersDTO.getEmail();
        this.password = usersDTO.getPassword();
        this.regStatus = usersDTO.getRegStatus();
        this.regDate = usersDTO.getRegDate();
        this.confirmDate = usersDTO.getConfirmDate();
    }

    public Users(Integer id, String firstName, String middleName, String lastName, String email, String password1) {
        super(email, password1, Collections.emptyList());
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password1;
    }

    public Users(Integer id, String firstName, String middleName, String lastName, String email, String password1, Integer regStatus) {
        super(email, password1, Collections.emptyList());
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password1;
        this.regStatus = regStatus;
    }

    public Users(Integer id, String firstName, String middleName, String lastName, String email, String password1, Integer regStatus, Timestamp regDate, Timestamp confirmDate) {
        super(email, password1, Collections.emptyList());
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password1;
        this.regStatus = regStatus;
        this.regDate = regDate;
        this.confirmDate = confirmDate;
    }

    public Users(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public Users(Users user, Set<GrantedAuthority> grants) {
        super(user.getUsername(),user.getPassword(),grants);
        this.id = user.id;
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.email = user.email;
        this.password = user.password;

    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public UsersDTO toDto() {
        return new UsersDTO(firstName, lastName, email, null);
    }
}