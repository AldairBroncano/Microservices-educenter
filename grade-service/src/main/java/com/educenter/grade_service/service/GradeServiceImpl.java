package com.educenter.grade_service.service;

import com.educenter.grade_service.entity.Grade;
import com.educenter.grade_service.repositoty.GradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService{

    private final GradeRepository gradeRepository;



    @Override
    public Grade saveGrade(Grade grade) {
        return gradeRepository.save(grade);
    }

    @Override
    public Grade updateGrade(Long id, Grade grade) {
        return gradeRepository.findById(id)
                .map(existingGrade -> {
                    existingGrade.setComment(grade.getComment());
                    existingGrade.setScore(grade.getScore());
                    existingGrade.setStudentId(grade.getStudentId());
                    existingGrade.setCourseId(grade.getCourseId());

                    return gradeRepository.save(existingGrade);

                })

                .orElseThrow( () -> new RuntimeException("Grade no encontrado con id: " +  id)  );
    }


    @Override
    public void deleteGrade(Long id) {
        gradeRepository.deleteById(id);
    }

    @Override
    public Grade getGrade(Long id) {
        return gradeRepository.findById(id).orElse(null);
    }

    @Override
    public List<Grade> getAllGrades() {
        return gradeRepository.findAll();
    }
}
