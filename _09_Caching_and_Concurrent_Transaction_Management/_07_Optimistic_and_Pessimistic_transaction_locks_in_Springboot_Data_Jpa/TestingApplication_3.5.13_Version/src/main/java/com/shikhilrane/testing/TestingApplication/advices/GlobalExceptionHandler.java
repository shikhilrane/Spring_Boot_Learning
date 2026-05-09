package com.shikhilrane.testing.TestingApplication.advices;

import com.shikhilrane.testing.TestingApplication.exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.StaleObjectStateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex) {
        log.error(ex.getLocalizedMessage());
        return ResponseEntity.internalServerError().build();
    }

    @ExceptionHandler(StaleObjectStateException.class)                            // Handles StaleObjectStateException globally.
    public ResponseEntity<?> handleStaleObjectState(StaleObjectStateException ex) {
        log.error(ex.getLocalizedMessage());                                      // Logs the exception error message.
        return new ResponseEntity<>("Stale data\n", HttpStatus.CONFLICT);   // Returns conflict response with message "Stale data".
    }
}
