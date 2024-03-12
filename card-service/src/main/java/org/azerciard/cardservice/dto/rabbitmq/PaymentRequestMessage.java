package org.azerciard.cardservice.dto.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class PaymentRequestMessage   {
    String productId;
    String userId;
    BigDecimal count;
    BigDecimal amount;
    String cvv;
    String cardNumber;
}
