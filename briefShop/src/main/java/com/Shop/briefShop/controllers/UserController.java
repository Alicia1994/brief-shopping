package com.Shop.briefShop.controllers;

import com.Shop.briefShop.dto.UserUpdateDto;
import com.Shop.briefShop.models.ERole;
import com.Shop.briefShop.models.Role;
import com.Shop.briefShop.models.User;
import com.Shop.briefShop.payload.request.SignupRequest;
import com.Shop.briefShop.payload.response.MessageResponse;
import com.Shop.briefShop.repositorys.RoleRepository;
import com.Shop.briefShop.repositorys.UserRepository;
import com.Shop.briefShop.security.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createAdmin")
    public ResponseEntity<?> registerAdminUser(@RequestBody SignupRequest signUpRequest, Authentication authentication) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                signUpRequest.getPresentation(),
                encoder.encode(signUpRequest.getPassword())
        );

        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN).get();
        user.setRoleId(adminRole.getId());
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("New Employer registered successfully!"));
    }

    @GetMapping("")
    public Iterable<User> listUser(){
        return userService.getAllUser();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") final Long id) {
        Optional<User> user = userService.getUser(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            return null;
        }
    }

    @PutMapping("/update/{id}")
    public User updateUser(@RequestBody UserUpdateDto userUpdateDto){
        Optional <User> optionalUser = userService.getUser(userUpdateDto.getId());
        userUpdateDto.setRoleId(optionalUser.get().getRoleId());
        userUpdateDto.setId(optionalUser.get().getId());

        if(optionalUser.isPresent()){
            return userService.updateUser(userUpdateDto);
        }else {
            return null;
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin")
    public User updateUProject(@RequestBody UserUpdateDto userUpdateDto){
        return userService.updateUser(userUpdateDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);

    }
}
