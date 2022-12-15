package com.usersservice.service;

import com.usersservice.entities.Users;
import com.usersservice.model.request.UpdateUserRequest;
import com.usersservice.repositories.RoleRepository;
import com.usersservice.repositories.UsersRepository;
import com.usersservice.service.Interface.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Users updateUser(UpdateUserRequest updateUserRequest) throws Exception {
        Users user = searchUserById(Math.toIntExact(updateUserRequest.getId()));
        if (user == null) {
            throw new Exception("Data User Not Found");
        }

        // validate username
        if (!updateUserRequest.getUsername().equalsIgnoreCase(user.getUsername())) {
            Boolean usernameExist = usersRepository.existsByUsername(updateUserRequest.getUsername());
            if (Boolean.TRUE.equals(usernameExist)) {
                log.info("Error: Username is already taken ! ");
                throw new Exception("Username is already taken!");
            }
        }

        // validate email
        if (!updateUserRequest.getEmail().equalsIgnoreCase(user.getEmail())) {
            Boolean emailExist = usersRepository.existsByEmail(updateUserRequest.getEmail());
            if (Boolean.TRUE.equals(emailExist)) {
                log.info("Error: Email is already taken ! ");
                throw new Exception("Email is already taken!");
            }
        }

        user.setId(updateUserRequest.getId());
        user.setUsername(updateUserRequest.getUsername());
        user.setAddress(updateUserRequest.getAddress());
        user.setEmail(updateUserRequest.getEmail());
        user.setPassword(passwordEncoder.encode(updateUserRequest.getPassword()));

        usersRepository.updateUser(Math.toIntExact(user.getId()), user.getUsername(), user.getAddress(), user.getEmail(), user.getPassword());

        return user;
    }

    @Override
    public void deleteUser(Integer id_user) throws Exception {
        Users usersResponse = usersRepository.findUsersById(id_user);
        if (usersResponse == null) {
            throw new Exception("Data User Not Found");
        }
        usersRepository.deleteRoleByIdUser(id_user);
        usersRepository.deleteByIdUser(id_user);
    }

    @Override
    public List<Users> findAllUser() {
        return usersRepository.findAllUsers();
    }

    @Override
    public Users searchUserById(Integer id_user) throws Exception {
        Users usersResponse = usersRepository.findUsersById(id_user);
        if (usersResponse == null) {
            throw new Exception("Data User Not Found");
        }
        return usersResponse;
    }

    @Override
    public List<Users> searchUserByName(String username) throws Exception {
        List<Users> usersResponse = usersRepository.findUsersByName(username);
        if (usersResponse.size() < 1) {
            throw new Exception("Data User Not Found");
        }
        return usersResponse;
    }
}
