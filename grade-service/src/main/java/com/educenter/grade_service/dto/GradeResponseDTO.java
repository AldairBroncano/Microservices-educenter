package com.educenter.grade_service.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GradeResponseDTO {


    private Long id;

    private Double score;

    private String comment;

    private CourseDTO course;

    private UserDTO student;


}
