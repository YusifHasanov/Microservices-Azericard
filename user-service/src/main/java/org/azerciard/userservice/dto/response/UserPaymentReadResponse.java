package org.azerciard.userservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Data
@Builder
public class UserPaymentReadResponse {
    String productName;
    String cardNumber;
    String cvv;
    BigDecimal amount;
    LocalDateTime paymentDate;
    BigDecimal count;
}
