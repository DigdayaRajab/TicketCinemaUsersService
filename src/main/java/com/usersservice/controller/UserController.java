package com.usersservice.controller;

import com.usersservice.entities.Users;
import com.usersservice.model.CommonResponseGenerator;
import com.usersservice.model.request.UpdateUserRequest;
import com.usersservice.service.Interface.UsersService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/user")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    @Autowired
    UsersService usersService;
    @Autowired
    CommonResponseGenerator commonResponseGenerator;

    @PutMapping(value = "/update", produces = "application/json", consumes = "application/json")
    public ResponseEntity updateUser(@RequestBody UpdateUserRequest paramUser) {
        try {
            Users response = usersService.updateUser(paramUser);
            return new ResponseEntity(commonResponseGenerator.successResponse(response, "successful update data"), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Update, Error : " + e.getMessage());
            return new ResponseEntity(commonResponseGenerator.failedClientResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteUser(@RequestParam("id") Integer id_user) {
        try {
            usersService.deleteUser(id_user);
            return new ResponseEntity(commonResponseGenerator.successResponse("", "successful delete data"), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Delete, Error : " + e.getMessage());
            return new ResponseEntity(commonResponseGenerator.failedClientResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping(value = "getAllUser")
    public ResponseEntity getAllUser() {
        try {
            List<Users> response = usersService.findAllUser();
            return new ResponseEntity(commonResponseGenerator.successResponse(response, "ok"), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Get All User , Error : " + e.getMessage());
            return new ResponseEntity(commonResponseGenerator.failedClientResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/search/{id_user}")
    public ResponseEntity searchById(@PathVariable("id_user") Integer idUser) {
        try {
            Users response = usersService.searchUserById(idUser);
            return new ResponseEntity(commonResponseGenerator.successResponse(response, "ok"), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Get User By id, Error : " + e.getMessage());
            return new ResponseEntity(commonResponseGenerator.failedClientResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/search")
    public ResponseEntity searchByUserName(@RequestParam("username") String usrName) {
        try {
            List<Users> response = usersService.searchUserByName(usrName);
            return new ResponseEntity(commonResponseGenerator.successResponse(response, "ok"), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Get User By Name, Error : " + e.getMessage());
            return new ResponseEntity(commonResponseGenerator.failedClientResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}