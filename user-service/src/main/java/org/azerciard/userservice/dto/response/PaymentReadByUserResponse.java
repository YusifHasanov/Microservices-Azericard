package org.azerciard.userservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Builder
public class PaymentReadByUserResponse {

    String id;

    String userId;

    String cardId;

    BigDecimal amount;

    String productId;
    LocalDateTime createdTime;
    BigDecimal count;
}
