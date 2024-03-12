package org.azerciard.paymentservice.mapper;

import org.azerciard.paymentservice.dto.rabbitmq.PaymentFinishMessage;
import org.azerciard.paymentservice.dto.response.PaymentReadByUserResponse;
import org.azerciard.paymentservice.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PaymentMapper {
    Payment mapToEntity(@MappingTarget Payment payment,PaymentFinishMessage dto);
    PaymentReadByUserResponse mapToDto(Payment entity);
}
    
    
  
 