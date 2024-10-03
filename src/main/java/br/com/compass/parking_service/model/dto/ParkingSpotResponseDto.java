package br.com.compass.parking_service.model.dto;

import br.com.compass.parking_service.model.entity.Vehicle;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class ParkingSpotResponseDto {

    @NotBlank
    private Long id;
    @NotBlank
    private Boolean reserved;
    @NotBlank
    private Boolean vacancy_occupied;
    @NotBlank
    private Vehicle occupied_by;

    public ParkingSpotResponseDto() {
    }

    public ParkingSpotResponseDto(Long id, Boolean reserved, Boolean vacancyOccupied, Vehicle occupiedBy) {
        this.id = id;
        this.reserved = reserved;
        this.vacancy_occupied = vacancyOccupied;
        this.occupied_by = occupiedBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getReserved() {
        return reserved;
    }

    public void setReserved(Boolean reserved) {
        this.reserved = reserved;
    }

    public Boolean getVacancy_occupied() {
        return vacancy_occupied;
    }

    public void setVacancy_occupied(Boolean vacancy_occupied) {
        this.vacancy_occupied = vacancy_occupied;
    }

    public Vehicle getOccupied_by() {
        return occupied_by;
    }

    public void setOccupied_by(Vehicle occupied_by) {
        this.occupied_by = occupied_by;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSpotResponseDto that = (ParkingSpotResponseDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ParkingSpotResponseDto{" +
               "id=" + id +
               ", reserved=" + reserved +
               ", vacancyOccupied=" + vacancy_occupied +
               ", occupiedBy=" + occupied_by +
               '}';
    }
}
