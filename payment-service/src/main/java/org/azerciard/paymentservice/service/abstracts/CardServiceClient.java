package org.azerciard.paymentservice.service.abstracts;

import org.azerciard.paymentservice.domain.model.dto.request.RequestById;
import org.azerciard.paymentservice.domain.model.dto.response.BaseResult;
import org.azerciard.paymentservice.dto.response.CardReadResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "card-service", url = "/card")
public interface CardServiceClient {

    @PostMapping("/by-id")
    BaseResult<CardReadResponse> validateToken(RequestById dto);
}