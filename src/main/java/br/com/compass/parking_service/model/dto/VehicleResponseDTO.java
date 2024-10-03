package br.com.compass.parking_service.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VehicleResponseDTO {
    private Long id;
    private String plate;
    private String category;
    @JsonProperty("type_vehicle")
    private String typeVehicle;
    private Boolean active;

    public VehicleResponseDTO() {
    }

    public VehicleResponseDTO(Long id, String plate, String category, String typeVehicle, Boolean active) {
        this.id = id;
        this.plate = plate;
        this.category = category;
        this.typeVehicle = typeVehicle;
        this.active = active;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTypeVehicle() {
        return typeVehicle;
    }

    public void setTypeVehicle(String typeVehicle) {
        this.typeVehicle = typeVehicle;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
/*
    @Override
    public String toString() {
        return "VehicleResponseDTO{" +
               "id=" + id +
               ", plate='" + plate + ''' +
        ", category='" + category + ''' +
        ", typeVehicle='" + typeVehicle + ''' +
        ", active=" + active +
        '}';
    }
*/
}