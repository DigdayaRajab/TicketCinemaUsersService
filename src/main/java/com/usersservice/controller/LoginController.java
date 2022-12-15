package com.usersservice.controller;

import com.usersservice.configuration.JwtUtils;
import com.usersservice.entities.Role;
import com.usersservice.entities.Users;
import com.usersservice.model.CommonResponseGenerator;
import com.usersservice.model.UserDetailsImpl;
import com.usersservice.model.enumerations.ERoles;
import com.usersservice.model.request.SignupRequest;
import com.usersservice.model.response.JwtResponse;
import com.usersservice.repositories.RoleRepository;
import com.usersservice.repositories.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class LoginController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    CommonResponseGenerator commonResponseGenerator;

    public LoginController() {}

    public LoginController(AuthenticationManager authenticationManager, UsersRepository usersRepository,
                           JwtUtils jwtUtils, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.usersRepository = usersRepository;
        this.jwtUtils = jwtUtils;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/api/user/signin")
    public ResponseEntity authenticateUser(@Valid @RequestBody Map<String, Object> login) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.get("username"), login.get("password"))
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        JwtResponse jwtResponse = new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getAddress(), userDetails.getEmail(), roles);
        return new ResponseEntity(commonResponseGenerator.successResponse( jwtResponse, "User registered successfully"), HttpStatus.OK);
    }

    @PostMapping("/api/user/signup")
    public ResponseEntity registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        try {
            // validate username
            Boolean usernameExist = usersRepository.existsByUsername(signupRequest.getUsername());
            if (Boolean.TRUE.equals(usernameExist)) {
                log.info("Error: Username is already taken ! ");
              return new ResponseEntity(commonResponseGenerator.failedClientResponse("400", "Username is already taken!"), HttpStatus.BAD_REQUEST);
            }
            // validate email
            Boolean emailExist = usersRepository.existsByEmail(signupRequest.getEmail());
            if (Boolean.TRUE.equals(emailExist)) {
                log.info("Error: Email is already taken ! ");
                return new ResponseEntity(commonResponseGenerator.failedClientResponse("400", "Email is already taken!"), HttpStatus.BAD_REQUEST);
            }

            Users users = new Users(signupRequest.getUsername(), signupRequest.getAddress(), signupRequest.getEmail(),
                    passwordEncoder.encode(signupRequest.getPassword()));

            Set<String> strRoles = signupRequest.getRole();
            Set<Role> roles = new HashSet<>();

            if (strRoles == null || strRoles.isEmpty()) {
                Role role = roleRepository.findByName(ERoles.CUSTOMER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                roles.add(role);
            } else {
                strRoles.forEach(role -> {
                    Role roles1 = roleRepository.findByName(ERoles.valueOf(role))
                            .orElseThrow(() -> new RuntimeException("Error: Role " + role + " is not found"));
                    roles.add(roles1);
                });
            }

            users.setRoles(roles);
            usersRepository.save(users);
            return new ResponseEntity(commonResponseGenerator.successResponse( users, "User registered successfully"), HttpStatus.OK);
        }catch (IllegalArgumentException ila){
            log.error("Error : " + ila.getMessage());
            return new ResponseEntity(commonResponseGenerator.failedClientResponse("404", "There is data that does not match"), HttpStatus.NOT_FOUND);
        }catch (Exception e){
            log.error("Error : " + e.getMessage());
            return new ResponseEntity(commonResponseGenerator.failedClientResponse("404", e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }


}

