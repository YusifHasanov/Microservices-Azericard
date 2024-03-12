package org.azerciard.productservice.service.abstracts;

import org.azerciard.productservice.domain.model.dto.response.BaseResult;
import org.azerciard.productservice.dto.request.ProductCreateRequest;
import org.azerciard.productservice.dto.request.ProductIdListRequest;
import org.azerciard.productservice.dto.response.ProductCreateResponse;
import org.azerciard.productservice.dto.response.ProductReadDetailResponse;
import org.azerciard.productservice.dto.response.ProductReadResponse;

import java.util.List;

public interface ProductService {
    BaseResult<ProductCreateResponse> createProduct(ProductCreateRequest dto);

    BaseResult<List<ProductReadResponse>> readAll();

    BaseResult<List<ProductReadDetailResponse>> getProductDetailsByUser(ProductIdListRequest dto);
}
