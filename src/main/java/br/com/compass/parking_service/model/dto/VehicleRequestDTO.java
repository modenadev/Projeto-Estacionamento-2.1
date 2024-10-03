package br.com.compass.parking_service.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.internal.bytebuddy.implementation.bind.annotation.Default;

public class VehicleRequestDTO {

    @NotNull
    @NotEmpty
    @NotBlank(message = "A placa do veiculo é obrigatória")
    private String plate;

    @NotNull
    @NotEmpty
    @NotBlank(message = "A categoria do veiculo é obrigatória")
    private String category;

    @JsonProperty("type_vehicle")
    @NotNull
    @NotEmpty
    @NotBlank(message = "O tipo de veículo é obrigatório")
    private String typeVehicle;

    private Boolean active;

    // Getters e Setters

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

    @Override
    public String toString() {
        return "VehicleRequestDTO{" +
               "plate='" + plate + '\'' +
               ", category='" + category + '\'' +
               ", typeVehicle='" + typeVehicle + '\'' +
               ", active=" + active +
               '}';
    }

}
