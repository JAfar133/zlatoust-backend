package com.zlatoust.exceptions.handler;

import com.zlatoust.exceptions.ZlatoustException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(ZlatoustException.class)
    public ResponseEntity<ApiError> handleSecurityEventHubException(ZlatoustException ex, WebRequest request) {
        return createResponseEntity(request, ex.getStatus(), ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGlobalException(Exception ex, WebRequest request) {
        return createResponseEntity(request, HttpStatus.BAD_REQUEST, ex.getMessage());
    }
//    @ExceptionHandler(MaxUploadSizeExceededException.class)
//    public ResponseEntity<String> handleMaxSizeException(MaxUploadSizeExceededException exc) {
//        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
//                .body("Файл слишком большой! Максимальный размер файла: 10MB.");
//    }

    private ResponseEntity<ApiError> createResponseEntity(WebRequest request, HttpStatus status, String message) {
        ApiError apiError = new ApiError(
                request.getDescription(false).substring(4),
                status,
                message);
        return new ResponseEntity<>(apiError, status);
    }

}
