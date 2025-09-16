package com.educenter.course_service.service;

import com.educenter.course_service.dto.UserProfileDTO;
import com.educenter.course_service.entity.Course;
import com.educenter.course_service.enums.Role;
import com.educenter.course_service.feign.AuthFeignClient;
import com.educenter.course_service.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService{


    private final CourseRepository repository;
    private final AuthFeignClient authFeignClient;




    @Override
    public List<Course> listar() {
        return repository.findAll();
    }

    @Override
    public Course saveCourse(Course course) {

        UserProfileDTO profesor = authFeignClient.getUserById(course.getProfesorId());
        System.out.println("Profesor encontrado: " + profesor);

        if (profesor == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "eL profesor no existe");

        }

        if (profesor.getRole() != Role.TEACHER) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario no es profesor");
        }




        return repository.save(course);
    }

    @Override
    public Course buscarPorID(Long id) {
        return repository.findById(id).orElse(null) ;
    }

    @Override
    public void eliminar(Long id) {
 repository.deleteById(id);
    }



    @Override
    public Course update(Long id, Course course) {
        Course existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con id " + id));


        existing.setNombre(course.getNombre());
        existing.setDescripcion(course.getDescripcion());
        existing.setProfesorId(course.getProfesorId());

        return repository.save(existing);

    }






}
