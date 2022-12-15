package com.usersservice.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersResponse {

    @Schema(example = "Id User")
    private Integer idUser;

    @Schema(example = "Rajab")
    private String username;

    @Schema(example = "rajab@gmail.com")
    private String email;

    @Schema(example = "Indonesia")
    private String address;

    @Schema(example = "rajabpass")
    private String password;
}
