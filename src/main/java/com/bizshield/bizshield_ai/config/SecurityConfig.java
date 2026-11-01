package com.bizshield.bizshield_ai.config;

import com.bizshield.bizshield_ai.security.CustomLogoutSuccessHandler;
import com.bizshield.bizshield_ai.security.LoginFailureHandler;
import com.bizshield.bizshield_ai.security.LoginSuccessHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomLogoutSuccessHandler logoutSuccessHandler;
    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailureHandler loginFailureHandler;

    public SecurityConfig(
            LoginSuccessHandler loginSuccessHandler,
            LoginFailureHandler loginFailureHandler,
            CustomLogoutSuccessHandler logoutSuccessHandler) {

        this.loginSuccessHandler = loginSuccessHandler;
        this.loginFailureHandler = loginFailureHandler;
        this.logoutSuccessHandler = logoutSuccessHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
            .csrf(csrf -> csrf
    .ignoringRequestMatchers(
        "/api/**",
        "/login",
        "/logout"
    )
)

            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/login.html",
                    "/login.css",
                    "/login.js",

                    "/logout-success.html",
                    "/logout-success.css",

                    "/images/**"
                ).permitAll()

                .requestMatchers("/", "/index.html").authenticated()

                .requestMatchers("/api/**").authenticated()

                .anyRequest().authenticated()
            )

            .exceptionHandling(exceptions -> exceptions
                .defaultAuthenticationEntryPointFor(
                    new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                    request -> request.getServletPath().startsWith("/api/")
                )
            )

            .formLogin(form -> form
                .loginPage("/login.html")
                .loginProcessingUrl("/login")

                .successHandler(loginSuccessHandler)

                .failureHandler(loginFailureHandler)

                .permitAll()
            )

            .httpBasic(Customizer.withDefaults())

            .logout(logout -> logout
                .logoutUrl("/logout")

                .logoutSuccessHandler(logoutSuccessHandler)

                .permitAll()
            );

        return http.build();
    }
}