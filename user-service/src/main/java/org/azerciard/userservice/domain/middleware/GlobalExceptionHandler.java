package org.azerciard.userservice.domain.middleware;




import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.azerciard.userservice.domain.exception.EntityException;
import org.azerciard.userservice.domain.exception.UnAuthorizedException;
import org.azerciard.userservice.domain.model.dto.response.ErrorResult;
import org.azerciard.userservice.domain.service.ExceptionManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
@AllArgsConstructor
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

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResult<String>> handleException(RuntimeException exception) {
        return exceptionManager.exceptionResponseGenerator(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResult<String>> handleException(Exception exception) {
        return exceptionManager.exceptionResponseGenerator(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
