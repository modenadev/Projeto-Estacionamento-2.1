package br.com.compass.parking_service.exceptions;


public class BadRequestException extends RuntimeException {

    public BadRequestException(String msg) {
        super(msg);
    }
}
