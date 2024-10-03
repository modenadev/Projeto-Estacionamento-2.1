package br.com.compass.parking_service.model.entity;

public enum TypeVehicle {
    PASSENGER_CAR(2),
    MOTORCYCLE (1),
    DELIVERY_TRUCK (4),
        PUBLIC_SERVICE (0);

    private final int value;

    TypeVehicle(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
