package com.educenter.auth_service.dto;

import lombok.Data;

@Data
public class UserLoginDTO {

    private String email;
    private String password;

}
