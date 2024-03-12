package org.azerciard.productservice.domain.model.dto.response;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PROTECTED)
@ToString
@EqualsAndHashCode
@SuperBuilder
@MappedSuperclass
public class BaseResult<T> {
    String messages;
    int statusCode;
    boolean success;
    T data;
}
