package org.azerciard.paymentservice.domain.middleware;


import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.azerciard.paymentservice.domain.Exception.EntityException;
import org.azerciard.paymentservice.domain.Exception.UnAuthorizedException;
import org.azerciard.paymentservice.domain.model.dto.response.ErrorResult;
import org.azerciard.paymentservice.domain.service.ExceptionManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
@AllArgsConstructor
@Log4j2
public class GlobalExceptionHandler {

    ExceptionManager exceptionManager;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResult<String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return exceptionManager.methodArgumentNotValidExceptionResponseGenerator(ex, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(EntityException.class)
    public ResponseEntity<ErrorResult<String>> handleEntitydException(EntityException exception) {
        return exceptionManager.exceptionResponseGenerator(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<ErrorResult<String>> handleUnAuthorizedException(UnAuthorizedException exception) {
        return exceptionManager.exceptionResponseGenerator(exception, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResult<String>> handleException(Exception exception) {
        return exceptionManager.exceptionResponseGenerator(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
