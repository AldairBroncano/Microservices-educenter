package com.educenter.grade_service.feign;

import com.educenter.grade_service.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service" , url="http://localhost:8082/api/user" )
public interface UserFeignClient {


@GetMapping("/{id}")
UserDTO getUserById(@PathVariable("id") long id);


}
