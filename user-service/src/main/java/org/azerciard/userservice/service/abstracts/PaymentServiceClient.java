package org.azerciard.userservice.service.abstracts;

import org.azerciard.userservice.domain.model.dto.response.BaseResult;
import org.azerciard.userservice.dto.response.PaymentReadByUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "payment-service", path = "/payment")
public interface PaymentServiceClient {

     @GetMapping("/user-payments")
     ResponseEntity<BaseResult<List<PaymentReadByUserResponse>>> getUserPayments();
}
