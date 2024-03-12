package org.azerciard.paymentservice.domain.model.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;


@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeleteSuccessResult<T> extends BaseResult<T> {

    public DeleteSuccessResult( T data) {
        super("successfully deleted", 204, true, data);
    }

    public DeleteSuccessResult( String message,T data) {
        super(message, 204, true, data);
    }


}
