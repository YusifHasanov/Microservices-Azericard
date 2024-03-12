package org.azerciard.cardservice.dto.response;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Builder
public final class CardReadResponse {
    String id;

    String userId;

    String cardNumber;

    LocalDate expirationDate;

    String cvv;

    BigDecimal balance;
}
