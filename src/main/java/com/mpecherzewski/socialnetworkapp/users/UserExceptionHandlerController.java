package com.mpecherzewski.socialnetworkapp.users;

import com.mpecherzewski.socialnetworkapp.users.dto.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class UserExceptionHandlerController {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    public ResponseEntity<String> handleUserNotFoundError(UserNotFoundException exception) {
        log.error("Requested users not found: {}, {}", exception.getErrorCode(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getErrorCode());
    }
}
