package com.cts.apigateway.security;

import com.cts.apigateway.entity.UserInfo;
import com.cts.apigateway.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

//@Component
public class UserInfoUserDetailsService implements UserDetailsService {

    @Autowired
    private UserInfoRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername method");
        Optional<UserInfo> userResult = repository.findByUsername(username);
        return userResult.map(UserInfoUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found "+username));
    }
}
