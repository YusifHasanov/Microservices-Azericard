package org.azerciard.paymentservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Builder
public class PaymentCreateRequest {

//    @NotBlank(message = "CardId Cannot be empty")
//    String cardId;

//    @NotNull(message = "Amount Cannot be null")
//    BigDecimal amount;

    @NotBlank(message = "ProductId Cannot be empty")
    String productId;

    @NotNull(message = "Count Cannot be empty")
    BigDecimal count;

    @NotBlank(message = "Cvv Cannot be empty")
    String cvv;

    @NotBlank(message = "CardNumber Cannot be empty")
    String cardNumber;
}
