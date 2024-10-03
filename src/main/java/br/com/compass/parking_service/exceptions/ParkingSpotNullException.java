package br.com.compass.parking_service.exceptions;

public class ParkingSpotNullException extends NullPointerException {
    public ParkingSpotNullException(String s) {
        super(s);
    }
}
