package org.azerciard.userservice.service.concrete;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.azerciard.userservice.domain.model.dto.request.RequestById;
import org.azerciard.userservice.domain.model.dto.response.BaseResult;
import org.azerciard.userservice.domain.model.dto.response.SuccessResult;
import org.azerciard.userservice.dto.request.CardIdListRequest;
import org.azerciard.userservice.dto.request.ProductIdListRequest;
import org.azerciard.userservice.dto.request.UserLoginRequest;
import org.azerciard.userservice.dto.request.UserRegisterRequest;
import org.azerciard.userservice.dto.response.*;
import org.azerciard.userservice.entity.User;
import org.azerciard.userservice.exception.*;
import org.azerciard.userservice.mapper.UserMapper;
import org.azerciard.userservice.repository.UserRepository;
import org.azerciard.userservice.service.abstracts.CardServiceClient;
import org.azerciard.userservice.service.abstracts.PaymentServiceClient;
import org.azerciard.userservice.service.abstracts.ProductServiceClient;
import org.azerciard.userservice.service.abstracts.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public final class UserServiceImpl implements UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    JwtService jwtService;
    PasswordEncoder passwordEncoder;
    PaymentServiceClient paymentServiceClient;
    CardServiceClient cardServiceClient;
    ProductServiceClient productServiceClient;

    public BaseResult<UserRegisterResponse> register(UserRegisterRequest dto) {
        if (!dto.getPassword().equals(dto.getConfirmPassword()))
            throw new PasswordsDontMatchException();

        if (userRepository.existsByPhoneNumber(dto.getPhoneNumber()))
            throw new UserAlreadyExistsWithPhoneNumberException();

        var newUser = new User();
        newUser = userMapper.userCreateDTOToUser(newUser, dto);
        newUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(newUser);

        String token = jwtService.generateToken(newUser);

        var resposne = UserRegisterResponse.builder()
                .accessToken(token)
                .build();

        return new SuccessResult<>(resposne);

    }

    public BaseResult<UserLoginResponse> login(UserLoginRequest dto) {

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(UserNotFoundException::new);

        boolean isPasswordMatch = passwordEncoder.matches(dto.getPassword(), user.getPassword());

        if (!isPasswordMatch)
            throw new UserPasswordWrongException();

        String token = jwtService.generateToken(user);

        var response = UserLoginResponse.builder()
                .accessToken(token)
                .build();

        return new SuccessResult<>(response);
    }

    @Override
    public BaseResult<Boolean> exist(RequestById id) {

        var userId = getUserId();
        if (userId == null)
            return new SuccessResult<>(false);

        if (userId.equals(id.getId()))
            return new SuccessResult<>(true);

        boolean isExist = userRepository.existsById(id.getId());
        return new SuccessResult<>(isExist);
    }

    @Override
    public BaseResult<String> validateToken(RequestById id) {
        var claims = jwtService.extractUsername(id.getId());

        User user = userRepository.findById(claims)
                .orElseThrow(UserNotFoundException::new);

        if (!jwtService.isTokenValid(id.getId(), user))
            throw new TokenIsNotValidException();

        return new SuccessResult<>(user.getId());

    }


    public BaseResult<List<UserPaymentReadResponse>> getUserTransaction() {
        //butunnpaymentleri gelir sonra burdai productidleri alir ve product servise gonderir
        //product servisde productlari alir ve onlari gonderir

        var result = paymentServiceClient.getUserPayments().getBody();

        if (result == null || !result.isSuccess())
            return new SuccessResult<>(List.of());

        List<PaymentReadByUserResponse> payments = result.getData();

        Set<String> cardIds = payments.stream()
                .map(PaymentReadByUserResponse::getCardId)
                .collect(Collectors.toSet());

        Set<String> productIds = payments.stream()
                .map(PaymentReadByUserResponse::getProductId)
                .collect(Collectors.toSet());

        var cardResult = cardServiceClient.getCardDetailByIdList(new CardIdListRequest(cardIds.stream().toList()))
                .getBody();

        if (cardResult == null || !cardResult.isSuccess())
            return new SuccessResult<>(List.of());

        List<CardReadDetailResponse> cards = cardResult.getData();

        var productResult = productServiceClient.getCardDetailByIdList(new ProductIdListRequest(productIds.stream().toList()))
                .getBody();

        if (productResult == null || !productResult.isSuccess())
            return new SuccessResult<>(List.of());

        List<ProductReadDetailResponse> products = productResult.getData();

        Map<String, CardReadDetailResponse> cardMap = cards.stream()
                .collect(Collectors.toMap(CardReadDetailResponse::getId, Function.identity()));

        Map<String, ProductReadDetailResponse> productMap = products.stream()
                .collect(Collectors.toMap(ProductReadDetailResponse::getId, Function.identity()));

        var response = payments.stream().map(p -> {
            CardReadDetailResponse card = cardMap.get(p.getCardId());
            ProductReadDetailResponse product = productMap.get(p.getProductId());

            String productName = product != null ? product.getName() : null;
            String cardNumber = card != null ? card.getCardNumber() : null;
            String cvv = card != null ? card.getCvv() : null;
            BigDecimal amount = p.getAmount();
            LocalDateTime paymentDate = p.getCreatedTime();
            BigDecimal count = p.getCount();
            return UserPaymentReadResponse.builder()
                    .productName(productName)
                    .cardNumber(cardNumber)
                    .cvv(cvv)
                    .count(count)
                    .amount(amount)
                    .paymentDate(paymentDate)
                    .build();
        }).collect(Collectors.toList());

        return new SuccessResult<>(response);
    }

    private String getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            User user = (User) userDetails;
            return user.getId();
        }
        return null;
    }

}
