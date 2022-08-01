package com.test.api_0822_sh.exceptions.handler;

import com.test.api_0822_sh.exceptions.models.ErrorDetails;
import com.test.api_0822_sh.exceptions.users.UserNotFoundException;
import com.test.api_0822_sh.exceptions.users.UserUnauthorizedFieldException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handle not found exceptions
     *
     * @param ex exception lifted
     * @param response web request
     * @return response entity with custom details
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> springHandleNotFound(UserNotFoundException ex, WebRequest response) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), ex.getMessage(), response.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle unauthorized fields exceptions
     *
     * @param ex exception lifted
     * @param response web request
     * @return response entity with custom details
     */
    @ExceptionHandler(UserUnauthorizedFieldException.class)
    public ResponseEntity<?> springHandleUnauthorizedField(UserUnauthorizedFieldException ex, WebRequest response) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), ex.getMessage(), response.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }

    /**
     * Handle all other exceptions
     *
     * @param ex exception lifted
     * @param response web request
     * @return response entity with custom details
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest response) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), ex.getMessage(), response.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
