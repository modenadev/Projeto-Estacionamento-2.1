package br.com.compass.parking_service.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

public class ErrorMessage {
    @JsonIgnore
    private String path;

    @JsonIgnore
    private String method;

    @JsonIgnore
    private int status;

    @JsonIgnore
    private String statusText;

    private String error;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> errors;

    public ErrorMessage() {
    }

    public ErrorMessage(String path, String method, int status, String statusText, String error, Map<String, String> errors) {
        this.path = path;
        this.method = method;
        this.status = status;
        this.statusText = statusText;
        this.error = error;
        this.errors = errors;
    }


    public String getPath() {
        return path;
    }

    public String getMethod() {
        return method;
    }

    public int getStatus() {
        return status;
    }

    public String getStatusText() {
        return statusText;
    }

    public String getError() {
        return error;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    @Override
    public String toString() {
        return "ErrorMessage{" +
                "errors=" + errors +
                '}';
    }

    public ErrorMessage(HttpServletRequest request,
                        HttpStatus status, String error) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.status = status.value();
        this.statusText = status.getReasonPhrase();
        this.error = error;
    }

    public ErrorMessage(HttpServletRequest request,
                        HttpStatus status, String error,
                        BindingResult result) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.status = status.value();
        this.statusText = status.getReasonPhrase();
        this.error = error;
        addErrors(result);

    }

    private void addErrors(BindingResult result) {
        this.errors = new HashMap<>();
        for (FieldError fieldError : result.getFieldErrors()){
            this.errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }
}