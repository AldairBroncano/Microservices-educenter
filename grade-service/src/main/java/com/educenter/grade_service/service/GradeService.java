package com.educenter.grade_service.service;

import com.educenter.grade_service.entity.Grade;

import java.util.List;

public interface GradeService {


    Grade saveGrade(Grade grade);
    Grade updateGrade(Long id, Grade grade);
    void deleteGrade(Long id);
    Grade getGrade(Long id);
    List<Grade> getAllGrades();

}
