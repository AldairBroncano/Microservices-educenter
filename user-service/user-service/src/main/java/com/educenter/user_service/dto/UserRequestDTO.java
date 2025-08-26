package com.educenter.user_service.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

     // Mismo que auth
    private String name;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;
    private String phone;
    @Column(name = "profile_phone")
    private String profilePhone;

}
