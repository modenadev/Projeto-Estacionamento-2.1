package br.com.compass.parking_service.exceptions;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        ExceptionResponse exceptionResponse = new ExceptionResponse
                ("Requested capacities invalid, it cant be null or negative.", request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
        @ExceptionHandler(NotFoundExcepetion.class)
        public ResponseEntity<ErrorMessage> NotFoundException (
                RuntimeException ex,
                HttpServletRequest request){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new ErrorMessage(request,
                            HttpStatus.NOT_FOUND, ex.getMessage()));
        }

        @ExceptionHandler(ConflictException.class)
        @ResponseStatus(HttpStatus.CONFLICT)
        public final ResponseEntity<ErrorMessage> ConflictException (ConflictException ex, HttpServletRequest request){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new ErrorMessage(request,
                            HttpStatus.CONFLICT, ex.getMessage()));
        }

        @ExceptionHandler(BadRequestException.class)
        public ResponseEntity<ErrorMessage> BadRequestException (RuntimeException ex,
                HttpServletRequest request){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new ErrorMessage(request,
                            HttpStatus.BAD_REQUEST, ex.getMessage()));
        }

    @ExceptionHandler
            (AccessDeniedException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValidException(
            AccessDeniedException ex,
            HttpServletRequest request,
            BindingResult result){
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request,
                        HttpStatus.FORBIDDEN,
                        ex.getMessage(), result));
    }


}
