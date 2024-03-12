package org.azerciard.cardservice.service.abstracts;

import org.azerciard.cardservice.domain.model.dto.request.RequestById;
import org.azerciard.cardservice.domain.model.dto.response.BaseResult;
import org.azerciard.cardservice.dto.request.CardIdListRequest;
import org.azerciard.cardservice.dto.response.CardReadDetailResponse;
import org.azerciard.cardservice.dto.response.CardReadResponse;

import java.util.List;

public interface CardService {
    BaseResult<Boolean> addNewCard();

    BaseResult<List<CardReadResponse>> getCardsByUser();

    BaseResult<CardReadResponse> getCardById(RequestById dto);
    BaseResult<List<CardReadDetailResponse>> getCardDetailsByUser(CardIdListRequest dto);
}
