package org.azerciard.userservice.service.abstracts;

import jakarta.validation.Valid;
import org.azerciard.userservice.domain.model.dto.response.BaseResult;
import org.azerciard.userservice.dto.request.ProductIdListRequest;
import org.azerciard.userservice.dto.response.ProductReadDetailResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
@FeignClient(name = "product-service", path = "/product")
public interface ProductServiceClient {

    @PostMapping("/detail-by-id-list")
    ResponseEntity<BaseResult<List<ProductReadDetailResponse>>> getCardDetailByIdList(@RequestBody @Valid ProductIdListRequest dto);

}
