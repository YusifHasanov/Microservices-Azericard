package org.azerciard.productservice.mapper;

import org.azerciard.productservice.dto.request.ProductCreateRequest;
import org.azerciard.productservice.dto.response.ProductCreateResponse;
import org.azerciard.productservice.dto.response.ProductReadDetailResponse;
import org.azerciard.productservice.dto.response.ProductReadResponse;
import org.azerciard.productservice.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {
    Product toEntity(@MappingTarget Product product, ProductCreateRequest dto);
    ProductCreateResponse toResponse(Product product);
    ProductReadResponse toReadResponse(Product product);
    ProductReadDetailResponse toReadDetailResponse(Product product);
}
    
    
  
 