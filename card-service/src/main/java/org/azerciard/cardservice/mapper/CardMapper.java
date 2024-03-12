package org.azerciard.cardservice.mapper;

import org.azerciard.cardservice.dto.response.CardReadDetailResponse;
import org.azerciard.cardservice.dto.response.CardReadResponse;
import org.azerciard.cardservice.entity.Card;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface CardMapper {
    CardReadResponse cardToCardReadResponse(Card card);
    CardReadDetailResponse cardToCardReadDetailResponse(Card card);
}
