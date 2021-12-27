package com.Shop.briefShop.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String presentation;
    private String role;
    private String password;
}
