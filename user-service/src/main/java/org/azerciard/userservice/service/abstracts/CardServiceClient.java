package org.azerciard.userservice.service.abstracts;

import jakarta.validation.Valid;
import org.azerciard.userservice.domain.model.dto.response.BaseResult;
import org.azerciard.userservice.dto.request.CardIdListRequest;
import org.azerciard.userservice.dto.response.CardReadDetailResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@FeignClient(name = "card-service", path = "/card")
public interface CardServiceClient {


    @PostMapping("/detail-by-id-list")
    ResponseEntity<BaseResult<List<CardReadDetailResponse>>> getCardDetailByIdList(@RequestBody @Valid CardIdListRequest dto);

}

