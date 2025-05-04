package com.example.qysqaserver.exceptions.handler;


import com.example.qysqaserver.dto.response.ErrorResponse;
import com.example.qysqaserver.exceptions.BadRequestException;
import com.example.qysqaserver.exceptions.DbNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.AccessDeniedException;
import java.util.Arrays;


@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(DbNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDbNotFoundException(DbNotFoundException ex) {
        log.error("DbNotFoundException exception: ", ex);

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getDescriptionKey());
        return ResponseEntity.status(ex.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {
        log.error("BadRequestException exception: ", ex);

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getDescriptionKey());

        return ResponseEntity.status(ex.getStatus()).body(errorResponse);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("MethodArgumentNotValidException exception: ", ex);

        String errorMessage = ex.getBindingResult().getAllErrors().getFirst().getDefaultMessage();

        ErrorResponse errorResponse = new ErrorResponse(
                "Invalid Request", ""
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(IllegalArgumentException ex) {
        log.error("IllegalArgumentException exception: ", ex);
        ErrorResponse errorResponse = new ErrorResponse(
                "Invalid Request", ""
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(ConstraintViolationException ex) {
        log.error("ConstraintViolationException exception: ", ex);
        ErrorResponse errorResponse = new ErrorResponse(
                "Invalid Request", ""
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(UsernameNotFoundException ex) {
        log.error("UsernameNotFoundException exception: ", ex);
        ErrorResponse errorResponse = new ErrorResponse(
                "Invalid Request", ""
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(AccessDeniedException ex) {
        log.error("AccessDeniedException exception: ", ex);
        ErrorResponse errorResponse = new ErrorResponse(
                "Forbidden Request", ""
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }


    private static String getStackTrace(Throwable ex) {
        filterStackTracesByProjectPackage(ex);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);

        return sw.toString();
    }

    private static void filterStackTracesByProjectPackage(Throwable ex) {
        if (ex == null) return;

        StackTraceElement[] stackTraces = Arrays.stream(ex.getStackTrace())
                .filter(se -> se.getClassName().startsWith("com."))
                .toArray(StackTraceElement[]::new);

        ex.setStackTrace(stackTraces);
    }
}
