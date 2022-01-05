package com.Shop.briefShop.controllers;

import com.Shop.briefShop.models.ERole;
import com.Shop.briefShop.models.Role;
import com.Shop.briefShop.models.User;
import com.Shop.briefShop.payload.request.LoginRequest;
import com.Shop.briefShop.payload.request.SignupRequest;
import com.Shop.briefShop.payload.response.JwtResponse;
import com.Shop.briefShop.payload.response.MessageResponse;
import com.Shop.briefShop.repositorys.RoleRepository;
import com.Shop.briefShop.repositorys.UserRepository;
import com.Shop.briefShop.security.jwt.JwtUtils;
import com.Shop.briefShop.security.services.impl.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String roles = userDetails.getRole();

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getPresentation(),
                roles));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
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
                encoder.encode(signUpRequest.getPassword()));

        String role = signUpRequest.getRole();
        Role roles = null;
//        Set<String> strRoles = signUpRequest.getRole();
//        Set<User> users = new HashSet<>();

        if (role.isEmpty()) {
            Role clientRole = roleRepository.findByName(ERole.ROLE_CLIENT)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            user.setRole(clientRole.getName().name());
            clientRole.getUsers().add(user);
//            roleRepository.save(clientRole);
        } else {
//            signupRequest.getRole().forEach(role -> {
                Role currentRole = roleRepository.findByName(ERole.valueOf(role))
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                user.setRole(currentRole.getName().name());
                currentRole.getUsers().add(user);
            System.out.println(currentRole);
//            roleRepository.save(currentRole);
//            }
//        );
                /*switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role mod is not found."));
                        roles = adminRole;
                        Optional<Role> roleOptional = roleRepository.findByName(ERole.ROLE_ADMIN);

                        System.out.println(roles);
                        user.setRole(roleOptional.get().getName().name());
                        user.setRole(adminRole.getName().name());
                        roles.getUsers().add(user);
                        System.out.println(roles);
                        break;

                    case "employer":
                        Role entrRole = roleRepository.findByName(ERole.ROLE_EMPLOYE)
                                .orElseThrow(() -> new RuntimeException("Error: Role mod is not found."));
                        roles = entrRole;
                        System.out.println(roles);
                        user.setRole(entrRole.getName().name());
                        roles.getUsers().add(user);
                        System.out.println(roles);
                        break;

                    case "client":
                        Role investRole = roleRepository.findByName(ERole.ROLE_CLIENT)
                                .orElseThrow(() -> new RuntimeException("Error: Role user is not found."));
                        roles = investRole;
                        System.out.println(roles);
                        user.setRole(roles.getName().name());
                        roles.getUsers().add(user);
                        System.out.println(roles);
                        break;

                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_CLIENT)
                                .orElseThrow(() -> new RuntimeException("Error: Role user is not found."));
                        roles = userRole;
                        System.out.println(roles);
                        user.setRole(userRole.getName().name());
                        roles.getUsers().add(user);
                        System.out.println(roles);
                        break;
                }*/
        };

        userRepository.save(user);
//        roleRepository.save(roles);

        System.out.println(user);
        System.out.println(role);
        System.out.println(roles);
//
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
        }
}