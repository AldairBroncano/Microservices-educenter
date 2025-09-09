package com.educenter.grade_service.controller;

import com.educenter.grade_service.dto.GradeResponseDTO;
import com.educenter.grade_service.entity.Grade;
import com.educenter.grade_service.service.GradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/grades")
public class GradeController {

private final GradeService gradeService;


    @PutMapping("/{id}")
    public ResponseEntity<Grade> updateGrade(@PathVariable Long id, @RequestBody Grade grade){
        return ResponseEntity.ok(gradeService.updateGrade(id,grade));
    }


    @PostMapping("/crear")
    public ResponseEntity<Grade> crearNota(@RequestBody Grade grade){

        Grade saved = gradeService.saveGrade(grade);

        return ResponseEntity.ok(saved);

    }



    @GetMapping("/list")
    public ResponseEntity<List<Grade>> listResponseEntity(){
        return ResponseEntity.ok(gradeService.getAllGrades());
    }


    @GetMapping("/{id}")
    public ResponseEntity<GradeResponseDTO>getGrade(@PathVariable Long id){
        return ResponseEntity.ok(gradeService.getGradeWithCourse(id));
    }


}
