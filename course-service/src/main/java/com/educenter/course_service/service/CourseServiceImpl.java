package com.educenter.course_service.service;

import com.educenter.course_service.entity.Course;
import com.educenter.course_service.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService{


    private final CourseRepository repository;




    @Override
    public List<Course> listar() {
        return repository.findAll();
    }

    @Override
    public Course saveCourse(Course course) {
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
