package com.educenter.course_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "cursos")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @Column(nullable = false)
    private String descripcion;

    @ElementCollection
    @CollectionTable(name = "curso_profesores", joinColumns = @JoinColumn(name = "curso_id"))
    @Column(name = "profesor_id")
    private List<Long> profesorIds = new ArrayList<>();


    @ElementCollection
    @CollectionTable(name = "curso_estudiantes", joinColumns = @JoinColumn(name = "curso_id"))
    @Column(name = "estudiante_id")
    private List<Long> studentIds = new ArrayList<>();


}
