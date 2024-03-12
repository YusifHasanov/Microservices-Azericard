package org.azerciard.paymentservice.service.concrete;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.azerciard.paymentservice.domain.Util.AppContext;
import org.azerciard.paymentservice.domain.model.dto.response.BaseResult;
import org.azerciard.paymentservice.domain.model.dto.response.SuccessResult;
import org.azerciard.paymentservice.dto.rabbitmq.PaymentFinishMessage;
import org.azerciard.paymentservice.dto.rabbitmq.PaymentRequestMessage;
import org.azerciard.paymentservice.dto.request.PaymentCreateRequest;
import org.azerciard.paymentservice.dto.response.PaymentReadByUserResponse;
import org.azerciard.paymentservice.entity.Payment;
import org.azerciard.paymentservice.mapper.PaymentMapper;
import org.azerciard.paymentservice.repository.PaymentRepository;
import org.azerciard.paymentservice.service.abstracts.PaymentService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentServiceImpl implements PaymentService {

    final RabbitTemplate rabbitTemplate;
    final PaymentMapper paymentMapper;
    final PaymentRepository paymentRepository;

    public PaymentServiceImpl(RabbitTemplate rabbitTemplate,
                              PaymentMapper paymentMapper,
                              PaymentRepository paymentRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.paymentMapper = paymentMapper;
        this.paymentRepository = paymentRepository;
    }


    @Override
    public BaseResult<String> pay(PaymentCreateRequest dto) {

        PaymentRequestMessage message = PaymentRequestMessage.builder()
                .cvv(dto.getCvv())
                .cardNumber(dto.getCardNumber())
                .count(dto.getCount())
                .productId(dto.getProductId())
                .userId(AppContext.getId())
                .build();
        System.out.println(dto.getCount());
        rabbitTemplate.convertAndSend("paymentExchange", "product.stock.check", message);

        return new SuccessResult<>("Payment request sent");
    }


    @RabbitListener(queues = "finishPayment")
    public void handlePaymentResult(PaymentFinishMessage request) {
        System.out.println("Payment finished");
        var amount = request.getAmount();
        var string = request.getCardId();
        System.out.println("amount" + amount);
        if (amount != null && string !=null) {
            var payment = paymentMapper.mapToEntity(new Payment(), request);
            paymentRepository.save(payment);
        }

    }


    @RabbitListener(queues = "paymentFailure")
    public void paymentFailure(PaymentFinishMessage request) {
        System.out.println("Payment failed");
    }


    public BaseResult<List<PaymentReadByUserResponse>> getUserPayments() {

        String userId = AppContext.getId();

        List<PaymentReadByUserResponse> payments = paymentRepository.findAllByUserId(userId).stream()
                .map(paymentMapper::mapToDto)
                .toList();

        return new SuccessResult<>(payments);
    }


}
