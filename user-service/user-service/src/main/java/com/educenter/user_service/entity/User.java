package com.educenter.user_service.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@Table(name = "usuarios_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id

    private Long id; // Mismo que auth
    private String name;

    @Column(name = "last_name")
    private String lastName;
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;
    private String phone;
    @Column(name = "profile_phone")
    private String profilePhone;


    





}
