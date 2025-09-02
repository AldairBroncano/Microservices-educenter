package com.educenter.grade_service.service;

import com.educenter.grade_service.dto.CourseDTO;
import com.educenter.grade_service.dto.GradeResponseDTO;
import com.educenter.grade_service.dto.UserDTO;
import com.educenter.grade_service.entity.Grade;
import com.educenter.grade_service.feign.CourseFeignClient;
import com.educenter.grade_service.feign.UserFeignClient;
import com.educenter.grade_service.repositoty.GradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService{

    private final GradeRepository gradeRepository;

    private final CourseFeignClient courseClient;

    private final UserFeignClient userClient;



    @Override
    public Grade saveGrade(Grade grade) {
        CourseDTO course = courseClient.getCourseById(grade.getCourseId());
        if (course == null) {
            throw new RuntimeException("El curso con ID " + grade.getCourseId() + " no existe");
        }
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

    @Override
    public GradeResponseDTO getGradeWithCourse(Long gradeId) {

        Grade grade = gradeRepository.findById(gradeId)
                .orElseThrow(() -> new RuntimeException("Grade not found"));

        CourseDTO course = courseClient.getCourseById(grade.getCourseId());
        UserDTO user = userClient.getUserById(grade.getStudentId());

        return GradeResponseDTO.builder()
                .id(grade.getId())
                .score(grade.getScore())
                .comment(grade.getComment())
                .course(course)
                .student(user)
                .build();



    }






}
