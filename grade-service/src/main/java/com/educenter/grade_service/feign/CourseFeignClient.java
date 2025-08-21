package com.educenter.grade_service.feign;

import com.educenter.grade_service.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "course-service")
public interface CourseFeignClient {



}
