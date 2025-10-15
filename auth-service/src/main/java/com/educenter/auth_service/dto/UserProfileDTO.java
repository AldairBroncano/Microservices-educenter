package com.educenter.auth_service.dto;


import com.educenter.auth_service.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDTO {

    private Long id;
    private String user;
    private String email;
    private Role role;





}





