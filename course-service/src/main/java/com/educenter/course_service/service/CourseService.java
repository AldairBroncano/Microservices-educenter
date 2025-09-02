package com.educenter.course_service.service;

import com.educenter.course_service.entity.Course;

import java.util.List;

public interface CourseService {


    List<Course> listar();
    Course saveCourse(Course course);
    Course buscarPorID(Long id);

    void eliminar(Long id);


    Course update(Long id, Course course);
}
