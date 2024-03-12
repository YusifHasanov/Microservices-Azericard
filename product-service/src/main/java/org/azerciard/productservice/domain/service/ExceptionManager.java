package org.azerciard.productservice.domain.service;

import org.azerciard.productservice.domain.model.dto.response.ErrorResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Service
public class ExceptionManager {

    public ResponseEntity<ErrorResult<String>> methodArgumentNotValidExceptionResponseGenerator(MethodArgumentNotValidException ex, HttpStatus httpStatus) {
        StringBuilder errors = new StringBuilder();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.append(error.getDefaultMessage()).append("; ");
        }

        var errorResponseBody = new ErrorResult<>(
                errors.toString(),
                httpStatus.value(),
                getExceptionMessage(ex)
        );
        return ResponseEntity
                .status(httpStatus)
                .body(errorResponseBody);
    }

    public ResponseEntity<ErrorResult<String>> exceptionResponseGenerator(Exception exception, HttpStatus httpStatus) {
        var errorResponseBody = new ErrorResult<>(
                exception.getMessage(),
                httpStatus.value(),
                getExceptionMessage(exception)
        );

        return ResponseEntity
                .status(httpStatus.value())
                .body(errorResponseBody);
    }

    public ErrorResult<String> exceptionResponseGenerator(Exception exception) {

        return new ErrorResult<>(
                exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                getExceptionMessage(exception)
        );
    }

    private String getExceptionMessage(Exception exception) {
        StackTraceElement[] stackTraceElements = exception.getStackTrace();
        String message = "";

        if (stackTraceElements.length > 0) {
            StackTraceElement callerStackTraceElement = stackTraceElements[0];
            message = "Method: " + callerStackTraceElement.getClassName() +
                    "." + callerStackTraceElement.getMethodName() +
                    "(" + callerStackTraceElement.getFileName() +
                    ":" + callerStackTraceElement.getLineNumber() + ")";
        }
        message += " ExceptionMessage: " + exception.getMessage();
        return message;
    }
}
