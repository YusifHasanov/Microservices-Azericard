package org.azerciard.cardservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.azerciard.cardservice.domain.model.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@SuperBuilder
@Table(name = "cards")
public class Card extends BaseEntity {

    @Column(name = "user_id")
    String userId;

    @Column(name = "card_number", unique = true, nullable = false, updatable = false)
    String cardNumber;

    @Column(name = "expiration_date", nullable = false, updatable = false)
    LocalDate expirationDate;

    @Column(name = "cvv", nullable = false, updatable = false)
    String cvv;

    @Column(name = "balance")
    BigDecimal balance;


    @Override
    public void prePersist() {
        super.prePersist();
        if (balance == null)
            balance = BigDecimal.ZERO;

        if(expirationDate == null)
            expirationDate = LocalDate.now().plusYears(5);
    }

}
