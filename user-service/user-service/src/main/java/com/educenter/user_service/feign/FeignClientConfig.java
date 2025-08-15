package com.educenter.user_service.feign;

import com.educenter.user_service.security.TokenContextHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        String token = TokenContextHolder.getToken();

        if(token != null && !token.isEmpty()){
            template.header("Authorization", token);
        }




    }


}
