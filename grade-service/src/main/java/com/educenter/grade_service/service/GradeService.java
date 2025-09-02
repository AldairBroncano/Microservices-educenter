package com.educenter.grade_service.service;

import com.educenter.grade_service.dto.GradeResponseDTO;
import com.educenter.grade_service.entity.Grade;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public interface GradeService {


    Grade saveGrade(Grade grade);
    Grade updateGrade(Long id, Grade grade);
    void deleteGrade(Long id);
    Grade getGrade(Long id);
    List<Grade> getAllGrades();
    GradeResponseDTO getGradeWithCourse(Long gradeId);


}
