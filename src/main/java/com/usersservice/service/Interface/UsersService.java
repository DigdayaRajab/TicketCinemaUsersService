package com.usersservice.service.Interface;

import com.usersservice.entities.Users;
import com.usersservice.model.request.UpdateUserRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UsersService {

    Users updateUser(UpdateUserRequest updateUserRequest) throws Exception;

    void deleteUser(Integer id_user) throws Exception;

    List<Users> findAllUser();

    Users searchUserById(Integer id_user) throws Exception;

    List<Users> searchUserByName(String username) throws Exception;
}
