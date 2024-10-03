package br.com.compass.parking_service.model.dto;

import br.com.compass.parking_service.model.entity.Vehicle;

public class ParkingSpotCreateDto {

    //
    private Boolean reserved;
    private Boolean vacancyOccupied;
    private Vehicle occupiedBy;

    public ParkingSpotCreateDto() {
    }

    public ParkingSpotCreateDto(Boolean reserved, Boolean vacancyOccupied, Vehicle occupiedBy) {
        this.reserved = reserved;
        this.vacancyOccupied = vacancyOccupied;
        this.occupiedBy = occupiedBy;
    }

    public Boolean getReserved() {
        return reserved;
    }

    public void setReserved(Boolean reserved) {
        this.reserved = reserved;
    }

    public Boolean getVacancyOccupied() {
        return vacancyOccupied;
    }

    public void setVacancyOccupied(Boolean vacancyOccupied) {
        this.vacancyOccupied = vacancyOccupied;
    }

    public Vehicle getOccupiedBy() {
        return occupiedBy;
    }

    public void setOccupiedBy(Vehicle occupiedBy) {
        this.occupiedBy = occupiedBy;
    }

    @Override
    public String toString() {
        return "ParkingSpotCreateDto{" +
               "reserved=" + reserved +
               ", vacancyOccupied=" + vacancyOccupied +
               ", occupiedBy=" + occupiedBy +
               '}';
    }
}
