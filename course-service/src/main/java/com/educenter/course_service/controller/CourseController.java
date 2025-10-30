package com.educenter.course_service.controller;

import com.educenter.course_service.entity.Course;
import com.educenter.course_service.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;


//@RequiredArgsConstructor
@RestController
@RequestMapping("api/courses")
public class CourseController {

    private final CourseService service;

    public CourseController(CourseService service) {
        this.service = service;
    }


    @GetMapping()
  //  @PreAuthorize("hasAnyRole('STUDENT','TEACHER','ADMIN')")
    public List<Course> getAll(){
        return service.listar();
    }



    @PostMapping()
  //  @PreAuthorize("hasAnyRole('ADMIN')")
    public Course createCurso(@RequestBody Course course){
        return service.saveCourse(course);
    }

    @GetMapping("/{id}")
 //   @PreAuthorize("hasAnyRole('STUDENT','TEACHER','ADMIN')")
    public ResponseEntity<Course> getById(@PathVariable Long id){
        Course curso = service.buscarPorID(id);
        return curso != null ? ResponseEntity.ok(curso) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
 //   @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Course> delete(@PathVariable Long id){
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
  //  @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Course>update(@PathVariable Long id, @RequestBody Course course){
        return ResponseEntity.ok(service.update(id, course));
}


    @PostMapping("/{courseId}/assign/{teacherId}")
public ResponseEntity<?> assignTeacherToCourse(@PathVariable Long courseId, @PathVariable Long teacherId){
       Course update =  service.assignTeacher(courseId, teacherId);
        return ResponseEntity.ok("Se asigno correctamente al curso");
}

    @PostMapping("/{courseId}/enroll/{studentId}")
    public ResponseEntity<?> enrollStudentToCourse(
            @PathVariable Long courseId,
            @PathVariable Long studentId) {

        Course updatedCourse = service.enrollStudent(courseId, studentId);
        return ResponseEntity.ok("✅ Estudiante inscrito correctamente al curso");
    }





}
