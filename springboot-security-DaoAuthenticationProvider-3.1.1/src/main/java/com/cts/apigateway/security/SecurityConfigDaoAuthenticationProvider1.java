package com.cts.apigateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//@Configuration
//@EnableWebSecurity
public class SecurityConfigDaoAuthenticationProvider1 {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        System.out.println("SecurityConfiguration securityFilterChain method");
        return httpSecurity.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                // for /api/employee/all endpoint no authorizations
                                .requestMatchers("/employee/all", "/employee/*").permitAll()
                                .requestMatchers("/employee/id/**").hasRole("USER")
                                .requestMatchers("/employee/name/**").hasRole("ADMIN")
                                .anyRequest()
                                .authenticated())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .httpBasic(Customizer.withDefaults()) // will create popup login UI in chrome. while testing by Postman always use httpBasic() only
                //.formLogin(Customizer.withDefaults())  // will create login page UI in web browser. wont work in Postman will show source code of html login page
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        System.out.println("SecurityConfiguration userDetailsService method");
        return new UserInfoUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
}

//Single Path Segment : * matches zero or more characters within a single path segment (i.e., between slashes). Example: /employee/* matches /employee/1, /employee/2, but not /employee/1/details.
//Multiple Path Segments : ** matches zero or more path segments. Example: /employee/** matches any URL that starts with /employee/, including /employee/1, /employee/1/details, /employee/2/details/reports, and so on.
