package com.educenter.auth_service.mapper;

import com.educenter.auth_service.dto.AuthResponseDTO;
import com.educenter.auth_service.dto.AuthRegisterDTO;
import com.educenter.auth_service.entity.Auth;

public class AuthMapper {

    public static AuthResponseDTO toDTO(Auth auth){
        AuthResponseDTO dto = new AuthResponseDTO();

     dto.setId(auth.getId());
     dto.setUser(auth.getUser());
     dto.setEmail(auth.getEmail());


     return dto;
    }



    public static Auth toEntity(AuthRegisterDTO dto) {
        Auth auth = new Auth();
        auth.setUser(dto.getUser());
        auth.setEmail(dto.getEmail());
        auth.setPassword(dto.getPassword()); // Aquí encriptarás luego
        return auth;
    }




}
