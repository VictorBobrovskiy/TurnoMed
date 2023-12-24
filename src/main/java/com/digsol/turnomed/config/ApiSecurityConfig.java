package com.digsol.turnomed.config;

import com.digsol.turnomed.config.TurnomedAuthenticationManager;
import com.digsol.turnomed.dto.UsersDTO;
import com.digsol.turnomed.model.Users;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Configuration
@EnableWebSecurity
public class ApiSecurityConfig {

    private final TurnomedAuthenticationManager manager;
    private final Gson gson;

    @Autowired
    public ApiSecurityConfig(TurnomedAuthenticationManager manager, Gson gson) {
        this.manager = manager;
        this.gson = gson;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(loginUrlAuthenticationEntryPoint())
                )
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers(HttpMethod.GET, "/**").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/auth/login", "/auth/heartbeat", "/auth/update",
                                "/auth/login", "/auth/heartbeat", "/auth/update",
                                "/events/reserve/**", "/events/update", "/clients/update",
                                "/addresses/*").authenticated()
                        .anyRequest().permitAll()
                )
                .formLogin(Customizer.withDefaults())
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .rememberMe(rememberMe -> rememberMe
                        .rememberMeServices(rememberMeServices())
                        .key("your-remember-me-key") // Replace with a suitable key
                )
                .requestCache(requestCache -> requestCache
                        .requestCache(new NullRequestCache())
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"))
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                );

        return http.build();
    }

    @Bean
    AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(jakarta.servlet.http.HttpServletRequest req,
                                                jakarta.servlet.http.HttpServletResponse resp,
                                                Authentication authentication) throws IOException {
                UsersDTO dto = ((Users) authentication.getPrincipal()).toDto();
                dto.setToken(req.getSession().getId());
                resp.setHeader("Content-Type", "application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(gson.toJson(dto));
            }
        };
    }

    @Bean
    AuthenticationFailureHandler authenticationFailureHandler() {
        return new AuthenticationFailureHandler() {


            @Override
            public void onAuthenticationFailure(jakarta.servlet.http.HttpServletRequest httpServletRequest,
                                                jakarta.servlet.http.HttpServletResponse response,
                                                AuthenticationException e
            ) throws IOException {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            }
        };
    }

    @Bean
    LoginUrlAuthenticationEntryPoint loginUrlAuthenticationEntryPoint() {
        return new LoginUrlAuthenticationEntryPoint("/auth/login") {
            @Override
            public void commence(jakarta.servlet.http.HttpServletRequest request,
                                 jakarta.servlet.http.HttpServletResponse response,
                                 AuthenticationException authException) throws IOException {
                if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                } else {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                }
            }
        };
    }

    @Bean
    LogoutSuccessHandler logoutSuccessHandler() {
        return new LogoutSuccessHandler() {
            @Override
            public void onLogoutSuccess(jakarta.servlet.http.HttpServletRequest httpServletRequest,
                                        jakarta.servlet.http.HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException {
                httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            }
        };
    }

    @Bean
    RememberMeServices rememberMeServices() {
        SpringSessionRememberMeServices services = new SpringSessionRememberMeServices();
        services.setAlwaysRemember(true);
        return services;
    }

    @Bean
    public HttpSessionIdResolver httpSessionIdResolver() {
        return HeaderHttpSessionIdResolver.xAuthToken();
    }

}


