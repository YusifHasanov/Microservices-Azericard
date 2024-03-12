package org.azerciard.paymentservice.dto.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class PaymentRequestMessage {
    String cvv;
    String cardNumber;
//    BigDecimal amount;
    String productId;
    String userId;
    BigDecimal count;
}
