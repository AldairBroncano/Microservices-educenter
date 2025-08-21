package com.educenter.course_service.repository;

import com.educenter.course_service.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {



}
