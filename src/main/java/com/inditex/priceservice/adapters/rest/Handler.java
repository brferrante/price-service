package com.inditex.priceservice.adapters.rest;

import com.inditex.priceservice.adapters.dtos.ErrorResponse;
import com.inditex.priceservice.adapters.exceptions.PriceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.webjars.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.InvalidPropertiesFormatException;

@ControllerAdvice
public class Handler {

        @ExceptionHandler(Exception.class)
        public ResponseEntity<Object> handleGlobalException(Exception ex,
                                                            HttpServletRequest request, HttpServletResponse response) {
            if (ex instanceof NotFoundException || ex instanceof InvalidPropertiesFormatException || ex instanceof PriceNotFoundException) {
                return new ResponseEntity<>("Requested record was not found", HttpStatus.BAD_REQUEST);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        @ExceptionHandler(HttpClientErrorException.BadRequest.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public ResponseEntity<ErrorResponse> handleBadRequestException(HttpClientErrorException.BadRequest ex) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Bad Request");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
}
