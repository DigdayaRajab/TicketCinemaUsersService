package com.usersservice.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersRequest {
    private Integer idUser;

    @NonNull
    private String username;

    @NonNull
    private String email;

    @NonNull
    private String address;

    @NonNull
    private String password;
}
