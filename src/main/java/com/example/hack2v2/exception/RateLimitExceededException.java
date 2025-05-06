package com.example.hack2v2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
public class RateLimitExceededException extends RuntimeException {

    private final Long resetTimeInSeconds;

    public RateLimitExceededException(String message) {
        super(message);
        this.resetTimeInSeconds = null;
    }

    public RateLimitExceededException(String message, Long resetTimeInSeconds) {
        super(message);
        this.resetTimeInSeconds = resetTimeInSeconds;
    }

    public Long getResetTimeInSeconds() {
        return resetTimeInSeconds;
    }
}