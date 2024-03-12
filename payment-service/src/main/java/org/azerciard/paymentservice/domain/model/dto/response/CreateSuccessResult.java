package org.azerciard.paymentservice.domain.model.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateSuccessResult<T> extends BaseResult<T> {

    public CreateSuccessResult( T data) {
        super("successfully created", 201, true, data);
    }

    public CreateSuccessResult( String message,T data) {
        super(message, 201, true, data);
    }


}
