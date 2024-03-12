package org.azerciard.userservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.azerciard.userservice.domain.model.dto.request.RequestById;
import org.azerciard.userservice.domain.model.dto.response.BaseResult;
import org.azerciard.userservice.dto.request.UserLoginRequest;
import org.azerciard.userservice.dto.request.UserRegisterRequest;
import org.azerciard.userservice.dto.response.UserPaymentReadResponse;
import org.azerciard.userservice.service.abstracts.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/user")
public class UserController {
    UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BaseResult<?>> register(@RequestBody @Valid UserRegisterRequest dto) {
        return ResponseEntity.ok(userService.register(dto));
    }


    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BaseResult<?>> login(@RequestBody @Valid UserLoginRequest dto) {
        return ResponseEntity.ok(userService.login(dto));
    }

    @PostMapping("/exist")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BaseResult<?>> exist(@RequestBody @Valid RequestById dto) {
        return ResponseEntity.ok(userService.exist(dto));
    }


    @PostMapping("/validate-token")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BaseResult<String>> validateToken(@RequestBody @Valid RequestById dto) {
        return ResponseEntity.ok(userService.validateToken(dto));
    }


    @GetMapping("/transaction")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BaseResult<List<UserPaymentReadResponse>>> getUserTransaction() {
        return ResponseEntity.ok(userService.getUserTransaction());
    }


    @PostMapping("/test")
    @ResponseStatus(HttpStatus.OK)
    public String logout() {
        return "s";
    }

}
