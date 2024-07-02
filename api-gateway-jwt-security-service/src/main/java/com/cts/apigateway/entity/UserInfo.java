package com.cts.apigateway.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "USER_INFO")
public class UserInfo {

    @Id
    private int id;
    private String username;
    private String password;
    private String roles;
}
