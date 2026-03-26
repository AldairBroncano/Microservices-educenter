package com.educenter.auth_service.mapper;

import com.educenter.auth_service.dto.AuthResponseDTO;
import com.educenter.auth_service.dto.AuthRegisterDTO;
import com.educenter.auth_service.entity.Auth;

public final class AuthMapper {




    //*** convierte Auth (entity) → AuthResponseDTO***
    public static AuthResponseDTO toDTO(Auth auth){

        if(auth == null) return null;

        // objeto que se enviará al cliente
        AuthResponseDTO dto = new AuthResponseDTO();

        //copias datos desde la entidad
        dto.setId(auth.getId());
        dto.setUser(auth.getUser());
        dto.setEmail(auth.getEmail());

        //devuelves
        return dto;
    }

/*
    Entity (BD)
   ↓
    AuthMapper.toDTO()
   ↓
    DTO
   ↓
    Controller

    */

//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
// **** AuthRegisterDTO → Auth (entity) ******
    public static Auth toEntity(AuthRegisterDTO dto) {

        if(dto==null)return null;

        Auth auth = new Auth();

        //copias datos del request
        auth.setUser(dto.getUser());
        auth.setEmail(dto.getEmail());
        auth.setPassword(dto.getPassword()); // se encripta en service

        return auth;
    }
/*
    Request (JSON)
   ↓
    AuthRegisterDTO
   ↓
    AuthMapper.toEntity()
   ↓
    Auth (entity)
   ↓
    BD

*/

}
