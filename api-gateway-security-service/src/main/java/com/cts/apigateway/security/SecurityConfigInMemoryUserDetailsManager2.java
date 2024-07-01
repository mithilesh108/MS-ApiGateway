package com.cts.apigateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity  // will use @PreAuthorize("hasRole('ROLE_USER')") in controller class
public class SecurityConfigInMemoryUserDetailsManager2 {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        System.out.println("SecurityConfiguration securityFilterChain method");
        return httpSecurity.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/employee/all","/employee/*").permitAll()
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/employee/id/**","/employee/name/**","/employee")
                .authenticated().and().httpBasic().and().build();
        // BELOW CODE WORKING PERFECTLY BEFORE ACTIVATE REMOVE @PreAuthorize("hasRole('ROLE_USER')") FROM CONTROLLER
//        httpSecurity.csrf().disable()
//                .authorizeHttpRequests(authorizeRequests ->
//                        authorizeRequests
//                                // for /api/employee/all endpoint no authorizations
//                                .requestMatchers("/employee/all", "/employee/**").permitAll()
//                                .requestMatchers("/employee/id/**").hasRole("USER")
//                                .requestMatchers("/employee/name/**").hasRole("ADMIN")
//                                .anyRequest()
//                                .authenticated()
//                ).httpBasic(); // will create popup login UI in chrome. while testing by Postman always use httpBasic() only
//                //.formLogin();  // will create login page UI in web browser. wont work in Postman will show source code of html login page
//        return httpSecurity.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        System.out.println("SecurityConfiguration userDetailsService method");
        UserDetails user = User.withUsername("user")
                .password(passwordEncoder.encode("user"))
                .roles("USER")
                .build();
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder.encode("admin"))
                .roles("ADMIN")
                .build();
        UserDetails mk = User.withUsername("mk")
                .password(passwordEncoder.encode("mk"))
                .roles("ADMIN","USER")
                .build();
        return new InMemoryUserDetailsManager(user, admin, mk);
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

//Single Path Segment : * matches zero or more characters within a single path segment (i.e., between slashes). Example: /employee/* matches /employee/1, /employee/2, but not /employee/1/details.
//Multiple Path Segments : ** matches zero or more path segments. Example: /employee/** matches any URL that starts with /employee/, including /employee/1, /employee/1/details, /employee/2/details/reports, and so on.
