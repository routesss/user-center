package com.sola.usercenter.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 统一异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionErrorHandler {

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ErrorMessage> error(SecurityException e) {
        log.warn("SecurityException异常 : {}", e);
        ResponseEntity<ErrorMessage> response = new ResponseEntity<>(
            ErrorMessage.builder().message(e.getMessage()).status(HttpStatus.UNAUTHORIZED.value()).build(),
            HttpStatus.UNAUTHORIZED);
        return response;
    }
}

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class ErrorMessage {
    private String message;

    private Integer status;
}