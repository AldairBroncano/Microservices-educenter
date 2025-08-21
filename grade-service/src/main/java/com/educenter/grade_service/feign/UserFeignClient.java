package com.educenter.grade_service.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "user-service")
public interface UserFeignClient {




}
