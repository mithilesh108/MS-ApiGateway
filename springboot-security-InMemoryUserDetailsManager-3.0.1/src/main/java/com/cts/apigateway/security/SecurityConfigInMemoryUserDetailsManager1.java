package com.cts.apigateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

//@Configuration
//@EnableWebSecurity
public class SecurityConfigInMemoryUserDetailsManager1 {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        System.out.println("SecurityConfiguration securityFilterChain method");
        return httpSecurity.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/employee/all","/employee/*").permitAll()
                .requestMatchers("/employee/id/**","/employee/name/**").hasRole("USER")
                .requestMatchers("/employee").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .httpBasic()  // will create popup login UI in chrome. while testing by Postman always use httpBasic() only
                // will create login page UI in web browser. dont use in Postman will show source code of html login page
                //.formLogin().permitAll()
                .and()
                .build();
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
