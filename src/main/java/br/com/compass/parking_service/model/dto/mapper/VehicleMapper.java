package br.com.compass.parking_service.model.dto.mapper;

import br.com.compass.parking_service.model.dto.VehicleRequestDTO;
import br.com.compass.parking_service.model.dto.VehicleResponseDTO;
import br.com.compass.parking_service.model.entity.Vehicle;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class VehicleMapper {

    public final ModelMapper modelMapper;

    public VehicleMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Vehicle toEntity(VehicleRequestDTO dto) {
        return modelMapper.map(dto, Vehicle.class);
    }

    public VehicleResponseDTO toResponseDTO(Vehicle vehicle) {
        return modelMapper.map(vehicle, VehicleResponseDTO.class);
    }
}
