package com.educenter.course_service.feign;


import com.educenter.course_service.dto.UserProfileDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "auth-service")
public interface AuthFeignClient {


    @GetMapping("/api/auth/profile/{id}")
    UserProfileDTO getUserById(@PathVariable("id") Long id);



}
