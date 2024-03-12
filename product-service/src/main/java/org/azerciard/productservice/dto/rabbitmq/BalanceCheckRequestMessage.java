package org.azerciard.productservice.dto.rabbitmq;

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
public class BalanceCheckRequestMessage {
    BigDecimal amount;
    String cvv;
    String cardNumber;
    String productId;
    String userId;
    BigDecimal count;
}


