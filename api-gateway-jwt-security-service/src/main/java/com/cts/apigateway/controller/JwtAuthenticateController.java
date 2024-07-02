package com.cts.apigateway.controller;

import com.cts.apigateway.dto.AuthDTO;
import com.cts.apigateway.service.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
public class JwtAuthenticateController {

    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/token/{user}/{pwd}")
    public String authenticateAndCreateToken1(@PathVariable("user") String userName,
                                              @PathVariable("pwd") String password) {
        System.out.println("authenticateAndCreateToken1 user : " + userName + " Pwd : " + password);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
        if (authentication.isAuthenticated()) {
            return jwtTokenService.createToken(userName);
        } else
            throw new UsernameNotFoundException("User not found : " + userName);
    }

    @PostMapping("/token")
    public String authenticateAndCreateToken2(@RequestBody AuthDTO authDTO) {
        System.out.println("authenticateAndCreateToken2 : " + authDTO);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authDTO.getUsername(), authDTO.getPassword()));
        if (authentication.isAuthenticated())
            return jwtTokenService.createToken(authDTO.getUsername());
        else
            throw new UsernameNotFoundException("User not found : " + authDTO.getUsername());
    }
}
