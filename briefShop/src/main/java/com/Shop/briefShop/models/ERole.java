package com.Shop.briefShop.models;

import java.util.HashSet;
import java.util.Set;

public enum ERole {
    ROLE_USER,
    ROLE_MODERATOR,
    ROLE_CLIENT,
    ROLE_EMPLOYE,
    ROLE_ENTREPRENEUR,
    ROLE_ADMIN;

    public static Set<ERole> ConvertFromString(Set<String> role) {
        Set<ERole> roles = new HashSet<>();
        role.forEach(str -> roles.add(ERole.valueOf(str)));
        return roles;
    }

}
