package br.com.compass.parking_service.exceptions;

public class GateInvalidException extends RuntimeException {
    public GateInvalidException(String s) {
        super(s);
    }
}
