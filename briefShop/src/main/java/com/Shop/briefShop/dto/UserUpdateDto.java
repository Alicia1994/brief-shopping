package com.Shop.briefShop.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserUpdateDto {
    private Long id;
    private String username;
    private String email;
    private String presentation;
    private String password;
    private Long roleId;
}
