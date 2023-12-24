package com.digsol.turnomed.config

import com.digsol.turnomed.dto.UsersDTO
import com.digsol.turnomed.model.Users
import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.security.web.authentication.RememberMeServices
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import org.springframework.security.web.savedrequest.NullRequestCache
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices
import org.springframework.session.web.http.HeaderHttpSessionIdResolver
import org.springframework.session.web.http.HttpSessionIdResolver
import javax.servlet.http.HttpServletResponse
import java.io.IOException

@Configuration
@EnableWebSecurity
class ApiSecurityConfig @Autowired constructor(
        private val manager: TurnomedAuthenticationManager,
        private val gson: Gson
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
                .exceptionHandling { exceptionHandling ->
                exceptionHandling.authenticationEntryPoint(loginUrlAuthenticationEntryPoint())
        }
            .authorizeRequests { authorizeRequests ->
                authorizeRequests
                        .requestMatchers(HttpMethod.GET, "/**").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(
                                "/auth/login", "/auth/heartbeat", "/auth/update",
                                "/auth/login", "/auth/heartbeat", "/auth/update",
                                "/events/reserve/**", "/events/update", "/clients/update",
                                "/addresses/*"
                        ).authenticated()
                        .anyRequest().permitAll()
        }
            .formLogin(Customizer.withDefaults())
                .cors(Customizer.withDefaults())
                .csrf { csrf -> csrf.disable() }
            .rememberMe { rememberMe ->
                rememberMe
                        .rememberMeServices(rememberMeServices())
                        .key("your-remember-me-key") // Replace with a suitable key
        }
            .requestCache { requestCache ->
                requestCache
                        .requestCache(NullRequestCache())
        }
            .logout { logout ->
                logout
                        .logoutRequestMatcher(AntPathRequestMatcher("/auth/logout"))
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutSuccessHandler(HttpStatusReturningLogoutSuccessHandler())
        }

        return http.build()
    }

    @Bean
    fun authenticationSuccessHandler(): AuthenticationSuccessHandler {
        return AuthenticationSuccessHandler { req, resp, authentication ->
                val dto = (authentication.principal as Users).toDto()
            dto.token = req.session.id
            resp.setHeader("Content-Type", "application/json")
            resp.characterEncoding = "UTF-8"
            resp.writer.write(gson.toJson(dto))
        }
    }

    @Bean
    fun authenticationFailureHandler(): AuthenticationFailureHandler {
        return AuthenticationFailureHandler { _, response, e ->
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.message)
        }
    }

    @Bean
    fun loginUrlAuthenticationEntryPoint(): LoginUrlAuthenticationEntryPoint {
        return object : LoginUrlAuthenticationEntryPoint("/auth/login") {
            override fun commence(
                    request: javax.servlet.http.HttpServletRequest,
                    response: javax.servlet.http.HttpServletResponse,
                    authException: AuthenticationException
            ) {
                if ("OPTIONS".equals(request.method, ignoreCase = true)) {
                    response.status = HttpServletResponse.SC_NO_CONTENT
                } else {
                    response.status = HttpServletResponse.SC_UNAUTHORIZED
                }
            }
        }
    }

    @Bean
    fun logoutSuccessHandler(): LogoutSuccessHandler {
        return LogoutSuccessHandler { _, httpServletResponse, _ ->
                httpServletResponse.status = HttpServletResponse.SC_OK
        }
    }

    @Bean
    fun rememberMeServices(): RememberMeServices {
        val services = SpringSessionRememberMeServices()
        services.isAlwaysRemember = true
        return services
    }

    @Bean
    fun httpSessionIdResolver(): HttpSessionIdResolver {
        return HeaderHttpSessionIdResolver.xAuthToken()
    }
}
