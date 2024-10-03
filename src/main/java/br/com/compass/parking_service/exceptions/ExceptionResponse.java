package br.com.compass.parking_service.exceptions;

import java.io.Serializable;

public class ExceptionResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private String message;
    private String details;

    public ExceptionResponse(String message, String details) {
        this.message = message;
        this.details = details;
    }


    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    @Override
    public String toString() {
        return "ExceptionResponse{" +
                "message='" + message + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
