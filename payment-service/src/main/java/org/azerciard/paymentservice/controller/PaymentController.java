package org.azerciard.paymentservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.azerciard.paymentservice.domain.model.dto.response.BaseResult;
import org.azerciard.paymentservice.dto.request.PaymentCreateRequest;
import org.azerciard.paymentservice.dto.response.PaymentReadByUserResponse;
import org.azerciard.paymentservice.service.abstracts.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/payment")
public class PaymentController {

    PaymentService paymentService;

    @PostMapping("/pay")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BaseResult<String>> pay(@RequestBody @Valid PaymentCreateRequest dto) {
        return ResponseEntity.ok(paymentService.pay(dto));
    }

    @GetMapping("/user-payments")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BaseResult<List<PaymentReadByUserResponse>>> getUserPayments() {
        return ResponseEntity.ok(paymentService.getUserPayments());
    }
}
