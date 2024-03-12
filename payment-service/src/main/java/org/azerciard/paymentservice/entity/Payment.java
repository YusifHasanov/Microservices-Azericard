package org.azerciard.paymentservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.azerciard.paymentservice.domain.model.BaseEntity;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@SuperBuilder
@Table(name = "payments")
public class Payment extends BaseEntity {

    @Column(name = "user_id", nullable = false)
    String userId;

    @Column(name = "card_id", nullable = false)
    String cardId;

    @Column(name = "amount", nullable = false)
    BigDecimal amount;

    @Column(name = "count", nullable = false)
    BigDecimal count;

    @Column(name = "product_id", nullable = false)
    String productId;
}
