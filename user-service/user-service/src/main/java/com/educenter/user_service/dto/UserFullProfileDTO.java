package com.educenter.user_service.dto;


import com.educenter.user_service.enums.Role;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFullProfileDTO {

    private Long id;
    private String username;
    private String email;
    private Role role;
    private String name;

    private String lastName;

    private LocalDate fechaNacimiento;
    private String phone;

    private String profilePhone;
}
