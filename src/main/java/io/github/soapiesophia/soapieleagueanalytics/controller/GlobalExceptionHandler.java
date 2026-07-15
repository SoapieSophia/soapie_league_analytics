package io.github.soapiesophia.soapieleagueanalytics.controller;

import io.github.soapiesophia.soapieleagueanalytics.exception.RiotRateLimitException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RiotRateLimitException.class)
    public ResponseEntity<String> tratarRateLimit(RiotRateLimitException e) {

        return ResponseEntity
                .status(HttpStatus.TOO_MANY_REQUESTS)
                .body(e.getMessage());
    }

}