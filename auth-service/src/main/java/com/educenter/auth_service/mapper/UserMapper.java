package com.educenter.auth_service.mapper;

import com.educenter.auth_service.dto.UserResponseDTO;
import com.educenter.auth_service.dto.UserRegisterDTO;
import com.educenter.auth_service.entity.User;

public class UserMapper {

    public static UserResponseDTO toDTO(User user){
        UserResponseDTO dto = new UserResponseDTO();

     dto.setId(user.getId());
     dto.setUsername(user.getUsername());
     dto.setEmail(user.getEmail());


     return dto;
    }



    public static User toEntity(UserRegisterDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword()); // Aquí encriptarás luego
        return user;
    }




}
