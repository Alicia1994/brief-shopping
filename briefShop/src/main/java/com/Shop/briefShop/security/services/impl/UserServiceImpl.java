package com.Shop.briefShop.security.services.impl;

import com.Shop.briefShop.models.ERole;
import com.Shop.briefShop.models.Role;
import com.Shop.briefShop.models.User;
import com.Shop.briefShop.repositorys.RoleRepository;
import com.Shop.briefShop.repositorys.UserRepository;
import com.Shop.briefShop.security.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public User createNewAdmin(User user, String username){
        Optional<User> userOptional = userRepository.findByUsername(username);
        User newUser = null;
        Optional<Role> roleAdmin = roleRepository.findByName(ERole.ROLE_ADMIN);
        if(userOptional.isPresent() && roleAdmin.isPresent()){
            newUser = userRepository.save(user);
        }
        return newUser;
    }

    @Override
    public Iterable<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUser(final Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User saveUser(User u) {
        User save = userRepository.save(u);
        return save;
    }

    @Override
    public User updateUsersimple(User user) {
        User userr = modelMapper.map(user, User.class);
        return userRepository.save(userr);
    }

    public User updateUser(String username, User user){//nouvelle que je test
        Optional<User> currentUser = userRepository.findByUsername(username);
        User userr = modelMapper.map(currentUser, User.class);
        return userRepository.save(userr);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User deleteProject(Long id, String username) {
        return null;
    }

}