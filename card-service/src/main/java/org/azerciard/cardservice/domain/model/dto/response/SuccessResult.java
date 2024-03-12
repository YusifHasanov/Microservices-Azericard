package org.azerciard.cardservice.domain.model.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;


@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SuccessResult<T> extends BaseResult<T> {

    public SuccessResult(String messages, T data) {
        super(messages, 200, true, data);
    }

    public SuccessResult(T data) {
        super("success", 200, true, data);
    }


}
