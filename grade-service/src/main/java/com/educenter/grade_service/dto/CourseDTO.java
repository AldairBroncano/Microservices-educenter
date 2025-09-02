package com.educenter.grade_service.dto;

import lombok.Data;

@Data
public class CourseDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private String profesorId;

}
