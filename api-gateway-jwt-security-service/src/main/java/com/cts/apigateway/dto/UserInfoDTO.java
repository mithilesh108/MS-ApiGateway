package com.cts.apigateway.dto;

import lombok.Data;

@Data
public class UserInfoDTO {

    private int id;
    private String username;
    private String password;
    private String roles;
}
