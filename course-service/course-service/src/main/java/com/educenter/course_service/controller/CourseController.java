package com.educenter.course_service.controller;

import com.educenter.course_service.entity.Course;
import com.educenter.course_service.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("api/courses")
public class CourseController {

    private final CourseService service;

    @GetMapping("/listar")
    public List<Course> listaCursos(){
        return service.listar();
    }

    @PostMapping("/create")
    public Course createCurso(@RequestBody Course course){
        return service.saveCourse(course);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> obtenerCurso(@PathVariable Long id){
        Course curso = service.buscarPorID(id);
        return curso != null ? ResponseEntity.ok(curso) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Course> elimnarCurso(@PathVariable Long id){
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }


}
