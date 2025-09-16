package com.educenter.auth_service.dto;


import lombok.Data;

@Data
public class AuthRegisterDTO
{

    private Long id;
    private String username;
    private String email;
    private String password;

    public AuthRegisterDTO(Long id, String username, String email) {
    }
}
