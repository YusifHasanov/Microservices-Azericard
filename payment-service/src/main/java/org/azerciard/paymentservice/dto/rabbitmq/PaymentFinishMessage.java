package org.azerciard.paymentservice.dto.rabbitmq;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class PaymentFinishMessage {
    String cardId;
    @NotNull
    BigDecimal amount;
    String productId;
    String userId;
    BigDecimal count;
}
