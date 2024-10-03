package br.com.compass.parking_service.model.dto;

import br.com.compass.parking_service.model.entity.TypeVehicle;
import br.com.compass.parking_service.model.entity.Vehicle;
import jakarta.validation.constraints.NotBlank;

public class TicketCreateDto {
    @NotBlank
    private String plate;
    @NotBlank
    private TypeVehicle typeVehicle;

    public TicketCreateDto() {
    }

    public TicketCreateDto(String plate, TypeVehicle typeVehicle) {
        this.plate = plate;
        this.typeVehicle = typeVehicle;
    }

    public @NotBlank String getPlate() {
        return plate;
    }

    public void setPlate(@NotBlank String  plate) {
        this.plate = plate;
    }

    public @NotBlank TypeVehicle getTypeVehicle() {
        return typeVehicle;
    }

    public void setTypeVehicle(@NotBlank TypeVehicle typeVehicle) {
        this.typeVehicle = typeVehicle;
    }
}
