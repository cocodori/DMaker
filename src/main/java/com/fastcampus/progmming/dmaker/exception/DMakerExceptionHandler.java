package com.fastcampus.progmming.dmaker.exception;

import com.fastcampus.progmming.dmaker.dto.DMakerErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class DMakerExceptionHandler {

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(DMakerException.class)
    public DMakerErrorResponse exceptionHandler(
            DMakerException e,
            HttpServletRequest request) {
        log.error("errorCode: {}, url: {}, message: {}",
                e.getDMakerErrorCode(), request.getRequestURI(), e.getDetailMessage());

        return new DMakerErrorResponse(e.getDMakerErrorCode(), e.getDetailMessage());
    }

    @ExceptionHandler(value = {
            HttpRequestMethodNotSupportedException.class,
            MethodArgumentNotValidException.class
    })
    public DMakerErrorResponse handleBadRequest(
            Exception e,
            HttpServletRequest request
    ) {
        log.error("url: {}, message: {}",
                request.getRequestURI(), e.getMessage());

        return DMakerErrorResponse.builder()
                .errorCode(DMakerErrorCode.INVALID_REQUEST)
                .errorMessage(DMakerErrorCode.INVALID_REQUEST.getMessage())
                .build();
    }

    @ExceptionHandler(Exception.class)
    public DMakerErrorResponse handleException(
            Exception e,
            HttpServletRequest request
    ) {
        log.error("url: {}, message: {}",
                request.getRequestURI(), e.getMessage());

        return new DMakerErrorResponse(
                DMakerErrorCode.INTERNAL_SERVER_ERROR,
                DMakerErrorCode.INTERNAL_SERVER_ERROR.getMessage());
    }
}
