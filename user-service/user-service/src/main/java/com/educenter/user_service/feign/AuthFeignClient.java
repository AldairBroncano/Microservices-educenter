package com.educenter.user_service.feign;


import com.educenter.user_service.dto.UserProfileDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "auth-service",
        configuration = FeignClientConfig.class)
public interface AuthFeignClient {

    @GetMapping("/api/auth/profile/{id}")
    UserProfileDTO getUserById(@PathVariable("id") Long id);


  /*  @GetMapping("/me")
    UserProfileDTO getUserFromToken(@RequestHeader("Authorization") String token);
*/
}
