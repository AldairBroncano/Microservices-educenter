package com.educenter.auth_service.dto;

import com.educenter.auth_service.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthLoginResponseDTO {
    private String token;
    private Long id;
    private String email;
    private Role role;
}
