package com.educenter.auth_service.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRegisterDTO
{

    private Long id;
    private String user;
    private String email;
    private String password;

    public AuthRegisterDTO(Long id, String user, String email) {
    }

}
