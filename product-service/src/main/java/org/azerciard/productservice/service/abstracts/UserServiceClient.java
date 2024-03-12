package org.azerciard.productservice.service.abstracts;

import jakarta.validation.Valid;
import org.azerciard.productservice.domain.model.dto.request.RequestById;
import org.azerciard.productservice.domain.model.dto.response.BaseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service", path = "/user")
public interface UserServiceClient {

    @PostMapping("/validate-token")
    ResponseEntity<BaseResult<String>> validateToken(@RequestBody @Valid RequestById dto);
}