package com.educenter.course_service.service;

import com.educenter.course_service.dto.UserProfileDTO;
import com.educenter.course_service.entity.Course;
import com.educenter.course_service.enums.Role;
import com.educenter.course_service.feign.AuthFeignClient;
import com.educenter.course_service.repository.CourseRepository;
import feign.FeignException;
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

        // Si el curso viene sin profesores asignados, simplemente se guarda
        if (course.getProfesorIds() == null || course.getProfesorIds().isEmpty()) {
            System.out.println("Creando curso sin profesores asignados...");
            return repository.save(course);
        }

        // Validar cada profesor antes de guardar
        for (Long profesorId : course.getProfesorIds()) {
            try {
                UserProfileDTO profesor = authFeignClient.getUserById(profesorId);
                System.out.println("Profesor encontrado: " + profesor);

                if (profesor == null) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El profesor con ID " + profesorId + " no existe.");
                }

                if (profesor.getRole() != Role.TEACHER) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario con ID " + profesorId + " no tiene rol de profesor.");
                }

            } catch (FeignException.NotFound e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El profesor con ID " + profesorId + " no existe (no encontrado en AuthService).");
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al comunicarse con AuthService para profesor ID " + profesorId + ".");
            }
        }

        // Si pasa todas las validaciones, se guarda el curso
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
        existing.setProfesorIds(course.getProfesorIds());

        return repository.save(existing);

    }

    @Override
    public List<Course> listarCursosSinProfesor() {
        return List.of();
    }

    @Override
    public Course assignTeacher(Long courseId, Long teacherId) {
        Course course = repository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso no encontrado"));

        UserProfileDTO profesor = authFeignClient.getUserById(teacherId);

        if (profesor == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El profesor no existe");
        }
        if (profesor.getRole() != Role.TEACHER) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario no es profesor");
        }
        if (course.getProfesorIds().contains(teacherId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El profesor ya está asignado a este curso");
        }

        course.getProfesorIds().add(teacherId);

        // 🔹 Guardamos y devolvemos el curso actualizado
        return repository.save(course);
    }



}
