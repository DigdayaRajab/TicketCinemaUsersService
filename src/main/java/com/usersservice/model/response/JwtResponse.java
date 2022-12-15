package com.usersservice.model.response;


import lombok.Data;

import java.util.List;

@Data
public class JwtResponse {
    private Long id;
    private String username;
    private String address;
    private String email;
    private List<String> roles;
    private String token;
    private String type = "Bearer";

    public JwtResponse(String token, Long id, String username, String address, String email, List<String> roles) {
        this.id = id;
        this.username = username;
        this.address = address;
        this.email = email;
        this.roles = roles;
        this.token = token;
    }

}
