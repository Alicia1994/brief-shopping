package com.Shop.briefShop.security.services;

import com.Shop.briefShop.models.User;

import java.util.Optional;

public interface UserService {
    Iterable<User> getAllUser();
    Optional<User> getUser(Long id);
    User saveUser(User u);
    User createNewEmployé(User user, String username);
    void deleteUser(Long id);
}
