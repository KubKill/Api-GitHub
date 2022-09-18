package com.atipera.api.git_data.exceptions.exception_handler;

import com.atipera.api.git_data.exceptions.CustomException;
import com.atipera.api.git_data.exceptions.InconsistentContentException;
import com.atipera.api.git_data.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<CustomException> customHandleUserNotFoundException(Exception ex, WebRequest request) {

        CustomException errors = new CustomException();
        errors.setStatus("404");
        errors.setMessage("User not found in GitHub.");

        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InconsistentContentException.class)
    public ResponseEntity<CustomException> customHandleInconsistentContentException(Exception ex, WebRequest request) {

        CustomException errors = new CustomException();
        errors.setStatus("500");
        errors.setMessage("Internal server error. Try Again.");

        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
