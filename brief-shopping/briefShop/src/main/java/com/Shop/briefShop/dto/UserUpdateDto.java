package com.Shop.briefShop.dto;

import lombok.Data;

@Data
public class UserUpdateDto {
    private Long id;
    private String username;
    private String email;
    private String presentation;
    private String password;
    private String role;
}
