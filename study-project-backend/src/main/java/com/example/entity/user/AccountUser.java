package com.example.entity.user;

import lombok.Data;

@Data
public class AccountUser {
    int id;
    public String username;
    String email;
    public boolean isVip;

}