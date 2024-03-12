package org.azerciard.userservice.mapper;

import org.azerciard.userservice.dto.request.UserRegisterRequest;
import org.azerciard.userservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface UserMapper {
    User userCreateDTOToUser(@MappingTarget User user, UserRegisterRequest userCreateRequestDTO);
}
