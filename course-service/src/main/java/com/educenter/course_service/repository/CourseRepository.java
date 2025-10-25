package com.educenter.course_service.repository;

import com.educenter.course_service.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByProfesorIdsIsNull();


}
