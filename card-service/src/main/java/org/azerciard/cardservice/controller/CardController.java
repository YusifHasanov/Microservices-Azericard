package org.azerciard.cardservice.controller;

import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.azerciard.cardservice.domain.model.dto.request.RequestById;
import org.azerciard.cardservice.domain.model.dto.response.BaseResult;
import org.azerciard.cardservice.dto.request.CardIdListRequest;
import org.azerciard.cardservice.dto.response.CardReadDetailResponse;
import org.azerciard.cardservice.dto.response.CardReadResponse;
import org.azerciard.cardservice.service.abstracts.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/card")
public class CardController {
    CardService cardService;

    @PostMapping("/create")
    public ResponseEntity<BaseResult<Boolean>> create() {
        return ResponseEntity.ok(cardService.addNewCard());
    }

    @PostMapping("/by-id")
    public ResponseEntity<BaseResult<CardReadResponse>> getById(@RequestBody @Valid RequestById dto) {
        return ResponseEntity.ok(cardService.getCardById(dto));
    }

    @GetMapping("/by-user")
    public ResponseEntity<BaseResult<List<CardReadResponse>>> getByUser() {
        return ResponseEntity.ok(cardService.getCardsByUser());
    }

    @PostMapping("/detail-by-id-list")
    public ResponseEntity<BaseResult<List<CardReadDetailResponse>>> getCardDetailByIdList(@RequestBody @Valid CardIdListRequest dto) {
        return ResponseEntity.ok(cardService.getCardDetailsByUser(dto));
    }
}
