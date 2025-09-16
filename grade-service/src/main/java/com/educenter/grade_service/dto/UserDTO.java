package com.educenter.grade_service.dto;

import com.educenter.grade_service.Role;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDTO {

    private Long id;
    private String name;
    private String lastName;
    private Role role;




}
