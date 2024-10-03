package br.com.compass.parking_service.services;

import br.com.compass.parking_service.exceptions.BadRequestException;
import br.com.compass.parking_service.exceptions.NotFoundExcepetion;
import br.com.compass.parking_service.model.dto.VehicleRequestDTO;
import br.com.compass.parking_service.model.dto.VehicleResponseDTO;

import br.com.compass.parking_service.model.dto.mapper.VehicleMapper;
import br.com.compass.parking_service.model.entity.Category;
import br.com.compass.parking_service.model.entity.TypeVehicle;
import br.com.compass.parking_service.model.entity.Vehicle;
import br.com.compass.parking_service.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;


    @Autowired
    public VehicleService(VehicleRepository vehicleRepository, VehicleMapper vehicleMapper) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleMapper = vehicleMapper;
    }

    @Transactional
    public VehicleResponseDTO save(VehicleRequestDTO vehicleRequestDTO) {
        Vehicle vehicle = vehicleMapper.toEntity(vehicleRequestDTO);
        validateActive(vehicle);
        validatePlate(vehicle.getPlate());
        validateEnumValues(vehicle.getTypeVehicle(), vehicle.getCategory());
        validateVehicleType(vehicle.getTypeVehicle(), vehicle.getCategory());
        vehicleRepository
                .findByPlate(vehicle.getPlate())
                .ifPresentOrElse(existingVehicle -> {
                            throw new BadRequestException("Vehicle already exists in database.");
                        },
                        () -> {
                            vehicle.setPlate(vehicle.getPlate().toUpperCase());
                            vehicleRepository.save(vehicle);
                        });
        return vehicleMapper.toResponseDTO(vehicle);
    }

    @Transactional
    public VehicleResponseDTO getVehicleByPlate(String plate) {
        validatePlate(plate);
        return vehicleRepository
                .findByPlate(plate)
                .map(vehicleMapper::toResponseDTO)
                .orElseThrow(
                        () -> new NotFoundExcepetion("Plate not found")
                );
    }

    @Transactional
    public void deleteVehicle(Long id) {
        vehicleRepository.findById(id)
                .ifPresentOrElse(vehicle -> vehicleRepository.deleteById(id),
                        () -> {
                            throw new NotFoundExcepetion("Vehicle not found with id: " + id);
                        });
    }

    @Transactional
    public void update(Long id, VehicleRequestDTO vehicleRequestDTO) {
        Vehicle vehicle = vehicleMapper.toEntity(vehicleRequestDTO);
        validatePlate(vehicle.getPlate());
        validateActive(vehicle);
        validateVehicleType(vehicle.getTypeVehicle(), vehicle.getCategory());
        vehicleRepository.findById(id)
                .ifPresentOrElse(existingVehicle -> {
                            vehicle.setId(id);
                            vehicle.setPlate(vehicle.getPlate().toUpperCase());
                            vehicleRepository.save(vehicle);
                        },
                        () -> { throw new NotFoundExcepetion("Id not found with id: " + id);});
    }

    private void validateVehicleType(TypeVehicle type, Category category) {
        Map<TypeVehicle, List<Category>> invalidCombinations = Map.of(
                TypeVehicle.DELIVERY_TRUCK,
                List.of(Category.MONTHLY_PAYER, Category.PUBLIC_SERVICE, Category.CASUAL),
                TypeVehicle.PUBLIC_SERVICE,
                List.of(Category.MONTHLY_PAYER, Category.DELIVERY_TRUCK, Category.CASUAL),
                TypeVehicle.PASSENGER_CAR,
                List.of(Category.PUBLIC_SERVICE, Category.DELIVERY_TRUCK),
                TypeVehicle.MOTORCYCLE,
                List.of(Category.PUBLIC_SERVICE, Category.DELIVERY_TRUCK)
        );
        if (invalidCombinations.containsKey(type) &&
            invalidCombinations.get(type).contains(category)) {
            throw new BadRequestException(
                    String.format("%s cannot be %s.", type.name(), category.name()));
        }
    }

    private void validateEnumValues(TypeVehicle type, Category category) {
        if (type == null || category == null) {
            throw new BadRequestException("Please, insert a valid category or type of vehicle");
        }
    }

    private void validatePlate(String plate) {
        String regex = "^[A-Z]{3}-?\\d{4}$|^[A-Z]{3}\\d[A-Z]\\d{2}$";

        if(!Pattern.matches(regex, plate.toUpperCase())) {
            throw new BadRequestException("Invalid plate format");
        }
    }

    private void validateActive(Vehicle vehicle){
        if(Boolean.TRUE.equals(vehicle.getActive()) &&
           vehicle.getCategory() != Category.MONTHLY_PAYER){
            throw new BadRequestException("The 'active' field can only be true for Monthly Payers");
        }
    }
}
