package com.Shop.briefShop.security.services;

import com.Shop.briefShop.models.User;

import java.util.Optional;

public interface UserService {
    Iterable<User> getAllUser();
    Optional<User> getUser(Long id);
    User saveUser(User u);
    User createNewAdmin(User user, String username);

    User updateUsersimple(User user); //test
    User updateUser(String username, User user);//nouvelle que je test

    void deleteUser(Long id);
    User deleteProject(Long id, String username);
}
