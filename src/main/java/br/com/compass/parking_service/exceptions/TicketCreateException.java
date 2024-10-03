package br.com.compass.parking_service.exceptions;

public class TicketCreateException extends RuntimeException {
    public TicketCreateException(String message) {
        super(message);
    }
}
