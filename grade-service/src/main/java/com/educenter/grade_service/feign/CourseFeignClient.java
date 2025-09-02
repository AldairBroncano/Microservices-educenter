package com.educenter.grade_service.feign;

import com.educenter.grade_service.dto.CourseDTO;
import com.educenter.grade_service.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "course-service", url="http://localhost:8083/api/courses" )
public interface CourseFeignClient {


@GetMapping("/{id}")
CourseDTO getCourseById(@PathVariable("id") long id);

}
