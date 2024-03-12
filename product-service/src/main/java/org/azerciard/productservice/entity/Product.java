package org.azerciard.productservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.azerciard.productservice.domain.model.BaseEntity;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@SuperBuilder
@Table(name = "products")
public class Product extends BaseEntity {
    @Column(name = "name", nullable = false)
    String name;
    @Column(name = "stock", nullable = false)
    BigDecimal stock;
    @Column(name = "price", nullable = false)
    BigDecimal price;
}
