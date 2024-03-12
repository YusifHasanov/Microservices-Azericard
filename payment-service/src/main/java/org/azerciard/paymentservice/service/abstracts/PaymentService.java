package org.azerciard.paymentservice.service.abstracts;

import org.azerciard.paymentservice.domain.model.dto.response.BaseResult;
import org.azerciard.paymentservice.dto.request.PaymentCreateRequest;
import org.azerciard.paymentservice.dto.response.PaymentReadByUserResponse;

import java.util.List;

public interface PaymentService {
    BaseResult<String> pay(PaymentCreateRequest dto);

    BaseResult<List<PaymentReadByUserResponse>> getUserPayments();
}
