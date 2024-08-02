package com.example.pj_webshop.entities.errors;

import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Contract("_ -> new")
    private static @NotNull GlobalExceptionHandler.RequestData getRequestData(@NotNull ServletWebRequest request) {
        HttpServletRequest servletRequest = request.getRequest();
        String path = servletRequest.getRequestURI();
        String method = servletRequest.getMethod();
        String clientIp = servletRequest.getRemoteAddr();
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = servletRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(headerName, servletRequest.getHeader(headerName));
        }
        return new RequestData(path, method, clientIp, headers);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponse> handleNullPointerException(@NotNull NullPointerException exception, WebRequest request, Locale locale) {
        RequestData requestData = getRequestData((ServletWebRequest) request);

        CustomErrorResponse errorResponse = new CustomErrorResponse(HttpStatus.NOT_FOUND, "Error on fetching data: " + exception.getMessage(), requestData.path(), requestData.method(), requestData.clientIp(), requestData.headers());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConversionNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleConversionNotSupportedException(@NotNull ConversionNotSupportedException exception, WebRequest request, Locale locale) {
        RequestData requestData = getRequestData((ServletWebRequest) request);

        CustomErrorResponse errorResponse = new CustomErrorResponse(HttpStatus.NOT_FOUND, "Internal server error: " + exception.getMessage(), requestData.path(), requestData.method(), requestData.clientIp(), requestData.headers());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(@NotNull Exception exception, WebRequest request, Locale locale) {
        RequestData requestData = getRequestData((ServletWebRequest) request);

        CustomErrorResponse errorResponse = new CustomErrorResponse(HttpStatus.NOT_FOUND, "Failed to parse exception type. General exception returned: " + exception.getMessage(), requestData.path(), requestData.method(), requestData.clientIp(), requestData.headers());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    private record RequestData(String path, String method, String clientIp, Map<String, String> headers) {
    }

}
