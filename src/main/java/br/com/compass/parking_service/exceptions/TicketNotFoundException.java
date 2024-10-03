package br.com.compass.parking_service.exceptions;

public class TicketNotFoundException extends NotFoundExcepetion {
    public TicketNotFoundException(String s) {
        super(s);
    }
}
