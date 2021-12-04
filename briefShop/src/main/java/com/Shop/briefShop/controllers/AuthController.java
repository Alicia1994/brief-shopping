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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        Long roles = userDetails.getRole();

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
//        Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                signUpRequest.getPresentation(),
                signUpRequest.getRoleId(),
//                signUpRequest.getRoleName(),
                encoder.encode(signUpRequest.getPassword()));

        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
/*        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
            strRoles = userRole.getUsers().add(user);
        } else {
            strRoles.forEach(role -> {
                Role currentRole = roleRepository.findByName(ERole.valueOf(role))
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(currentRole);

            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }*/
         //Creer un nouveau compte utilisateur
   /*     User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                signUpRequest.getPresentation(),
                encoder.encode(signUpRequest.getPassword())
        );
        Set<String> strRoles = new HashSet();

        final Long[] roleId = {null};*/
/*
        strRoles.forEach(role -> {
                    switch (role) {
                        case "admin":
                            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                    .orElseThrow(() -> new RuntimeException("Error: Role  admin is not found."));
                            roleId[0] = adminRole.getId();
                            break;
                        case "entrepreneur":
                            Role entrRole = roleRepository.findByName(ERole.ROLE_ENTREPRENEUR)
                                    .orElseThrow(() -> new RuntimeException("Error: Role mod is not found."));
                            roleId[0] = entrRole.getId();
                            break;
                        case "investisseur":
                            Role clientRole = roleRepository.findByName(ERole.ROLE_CLIENT)
                                    .orElseThrow(() -> new RuntimeException("Error: Role user is not found."));
                            roleId[0] = clientRole.getId();
                            break;
                        default:

                    }});
*/

/*        user.setRoleId(roleId[0]);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    } */
    /*
    //    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
//        if (userRepository.existsByUsername(signupRequest.getUsername())) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error: Username is already taken!"));
//        }
//        if (userRepository.existsByEmail(signupRequest.getEmail())) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error: Email is already in use!"));
//        }
//
//        //Create new user's account
//        User user = new User(signupRequest.getUsername(),
//                signupRequest.getEmail(),
//                signupRequest.getPresentation(),
//                signupRequest.getNumber(),
//                encoder.encode(signupRequest.getPassword()));
//
//        Set<String> strRoles = signupRequest.getRole();
//        Set<Role> roles = new HashSet<>();
//
//        if (strRoles.isEmpty()) {
//            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//                    .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
//            roles.add(userRole);
//        } else {
//            signupRequest.getRole().forEach(role -> {
//                Role currentRole = roleRepository.findByName(ERole.valueOf(role))
//                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                roles.add(currentRole);
//
//            });
//        }
//
//        user.setRoles(roles);
//        userRepository.save(user);
//
//        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
//    }
    */

/*
* public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		User user=appService.getUser(username);
		if(user==null){
			throw new UsernameNotFoundException("User doesn`t exist");
		}

		//Fetching User roles form DB.
		Set<UserRole> userRoles=user.getUserRoles();
		List<String> dbRoles=new ArrayList<String>();
		for (UserRole userRole : userRoles) {
			dbRoles.add(userRole.getRole().getRoleName());
		}
		logger.debug("Roles of :"+username+" is "+dbRoles);

		// pass user object and roles to LoggedUser
		LoggedUser loggedUser=new LoggedUser(user, dbRoles);
		return loggedUser;
	}
	*/

}