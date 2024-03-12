package org.azerciard.userservice.domain.config.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.azerciard.userservice.domain.util.TokenUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

    @Bean
    public RequestInterceptor requestTokenBearerInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                TokenUtil.getTokenFromRequest().ifPresent(token -> {
                    template.header("Authorization", "Bearer " + token);
                });
            }
        };
    }
}