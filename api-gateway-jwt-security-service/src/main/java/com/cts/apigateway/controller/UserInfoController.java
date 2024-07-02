package com.cts.apigateway.controller;

import com.cts.apigateway.entity.UserInfo;
import com.cts.apigateway.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserInfoController {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping
    public ResponseEntity<String> createUserInfo(@RequestBody UserInfo userInfo){
        System.out.println("UserInfoController createUserInfo method "+userInfo);
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        System.out.println("Password Encrypted : "+userInfo);
        return ResponseEntity.status(HttpStatus.OK).body(userInfoRepository.save(userInfo).toString());
    }

    @GetMapping
    public ResponseEntity<List<UserInfo>> findAllUser(){
        System.out.println("UserInfoController findAllUser method ");
        return ResponseEntity.status(HttpStatus.OK).body(userInfoRepository.findAll());
    }
}
