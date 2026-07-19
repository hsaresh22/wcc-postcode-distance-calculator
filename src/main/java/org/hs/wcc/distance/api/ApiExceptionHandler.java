package org.hs.wcc.distance.api;

import org.hs.wcc.postcode.exception.InvalidPostcodeException;
import org.hs.wcc.postcode.exception.PostcodeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(InvalidPostcodeException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPostcode(InvalidPostcodeException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(PostcodeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUnknownPostcode(PostcodeNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getMessage()));
    }

    public record ErrorResponse(String message) {
    }
}
