package br.com.compass.parking_service.model.dto;

import br.com.compass.parking_service.model.entity.Vehicle;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ParkingSpotUpdateDto {

    //    private Boolean reserved;
//    private Boolean vacancy_occupied;
//    private Vehicle occupied_by;
//    private Boolean active_vacancy;
    @NotNull(message = "monthly_capacity cannot be null")
    @Positive(message = "monthly_capacity must be a positive number")
    private Long monthly_capacity;
    @NotNull(message = "general_capacity cannot be null")
    @Positive(message = "general_capacity must be a positive number")
    private Long general_capacity;

    public ParkingSpotUpdateDto() {
    }

//    public ParkingSpotUpdateDto(Boolean reserved, Boolean vacancy_occupied, Vehicle occupied_by, Boolean active_vacancy) {
//        this.reserved = reserved;
//        this.vacancy_occupied = vacancy_occupied;
//        this.occupied_by = occupied_by;
//        this.active_vacancy = active_vacancy;
//    }
//
//    public Boolean getReserved() {
//        return reserved;
//    }
//
//    public void setReserved(Boolean reserved) {
//        this.reserved = reserved;
//    }
//
//    public Boolean getVacancy_occupied() {
//        return vacancy_occupied;
//    }
//
//    public void setVacancy_occupied(Boolean vacancy_occupied) {
//        this.vacancy_occupied = vacancy_occupied;
//    }
//
//    public Vehicle getOccupied_by() {
//        return occupied_by;
//    }
//
//    public void setOccupied_by(Vehicle occupied_by) {
//        this.occupied_by = occupied_by;
//    }
//
//    public Boolean getActive_vacancy() {return active_vacancy;}
//
//    public void setActive_vacancy(Boolean active_vacancy) {this.active_vacancy = active_vacancy;}

    public Long getMonthly_capacity() {return monthly_capacity;}

    public Long getGeneral_capacity() {return general_capacity;}

//    @Override
//    public String toString() {
//        return "ParkingSpotCreateDto{" +
//                "reserved=" + reserved +
//                ", vacancyOccupied=" + vacancy_occupied +
//                ", occupiedBy=" + occupied_by +
//                ", active=" + active_vacancy +
//                '}';
//    }
}
