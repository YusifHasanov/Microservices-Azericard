package org.azerciard.userservice.service.abstracts;

import org.azerciard.userservice.domain.model.dto.request.RequestById;
import org.azerciard.userservice.domain.model.dto.response.BaseResult;
import org.azerciard.userservice.dto.request.UserLoginRequest;
import org.azerciard.userservice.dto.request.UserRegisterRequest;
import org.azerciard.userservice.dto.response.UserLoginResponse;
import org.azerciard.userservice.dto.response.UserPaymentReadResponse;
import org.azerciard.userservice.dto.response.UserRegisterResponse;

import java.util.List;

public interface UserService {
    BaseResult<UserRegisterResponse> register(UserRegisterRequest dto);

    BaseResult<UserLoginResponse> login(UserLoginRequest dto);

    BaseResult<Boolean> exist(RequestById id);
    BaseResult<String> validateToken(RequestById id);
    BaseResult<List<UserPaymentReadResponse>> getUserTransaction();
}
