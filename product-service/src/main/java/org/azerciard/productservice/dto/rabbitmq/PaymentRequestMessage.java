package org.azerciard.productservice.dto.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class PaymentRequestMessage {
    String cvv;
    String cardNumber;
    BigDecimal amount;
    String productId;
    String userId;
    BigDecimal count;

}
