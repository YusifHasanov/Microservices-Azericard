package org.azerciard.productservice.dto.request;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
public class ProductCreateRequest {
    @NotBlank(message = "Cannot be empty")
    String name;
    @NotNull(message = "Cannot be null")
    @Min(value = 0, message = "Stock cannot be less than 0")
    BigDecimal stock;
    @NotNull(message = "Cannot be null")
    @Min(value = 0, message = "Price cannot be less than 0")
    BigDecimal price;
}
