package com.educenter.course_service.dto;

import com.educenter.course_service.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDTO {

    private Long id;
    private String username;
    private String email;
    private Role role;


}
