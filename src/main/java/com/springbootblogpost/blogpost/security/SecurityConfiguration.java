package com.springbootblogpost.blogpost.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import java.security.Security;

@Configuration
//@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfiguration {

    private UserDetailsService userDetailsService;
    public SecurityConfiguration(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();

    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf(csrf ->csrf.disable())
                .authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers(HttpMethod.GET,"/api/**").permitAll()
                                .requestMatchers("/api/v1/users/auth/**").permitAll()
                                .anyRequest().authenticated()
                        );

//                .logout(logout -> logout
//                        .logoutUrl("/api/v1/users/auth/logout") // Logout URL
//                        .deleteCookies("JSESSIONID","X-XSRF-TOKEN")
//                                .invalidateHttpSession(true) // Invalidate session
//
//                        //Delete cookies
//                );

        return httpSecurity.build();
    }

}
