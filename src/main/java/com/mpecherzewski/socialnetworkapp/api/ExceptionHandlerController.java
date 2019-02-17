package com.mpecherzewski.socialnetworkapp.api;

import com.mpecherzewski.socialnetworkapp.domain.service.SocialNetworkValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    @ExceptionHandler(SocialNetworkValidationException.class)
    @ResponseBody
    public ResponseEntity<String> handlReviewServiceValidationError(SocialNetworkValidationException exception) {
        log.error("Validation error occurred: {}, {}", exception.getErrorCode(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getErrorCode());
    }

}
