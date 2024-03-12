package org.azerciard.productservice.controller;


import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.azerciard.productservice.domain.model.dto.response.BaseResult;
import org.azerciard.productservice.dto.request.ProductCreateRequest;
import org.azerciard.productservice.dto.request.ProductIdListRequest;
import org.azerciard.productservice.dto.response.ProductCreateResponse;
import org.azerciard.productservice.dto.response.ProductReadDetailResponse;
import org.azerciard.productservice.dto.response.ProductReadResponse;
import org.azerciard.productservice.service.abstracts.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/product")
public class ProductController {
    ProductService productService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BaseResult<ProductCreateResponse>> create(@RequestBody @Valid ProductCreateRequest dto) {
        return ResponseEntity.ok(productService.createProduct(dto));
    }


    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BaseResult<List<ProductReadResponse>>> read() {
        return ResponseEntity.ok(productService.readAll());
    }


    @PostMapping("/detail-by-id-list")
    public ResponseEntity<BaseResult<List<ProductReadDetailResponse>>> getCardDetailByIdList(@RequestBody @Valid ProductIdListRequest dto) {
        return ResponseEntity.ok(productService.getProductDetailsByUser(dto));
    }
}
