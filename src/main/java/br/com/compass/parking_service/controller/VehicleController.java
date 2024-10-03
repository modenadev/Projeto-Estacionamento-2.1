package br.com.compass.parking_service.controller;

import br.com.compass.parking_service.model.dto.VehicleRequestDTO;
import br.com.compass.parking_service.model.dto.VehicleResponseDTO;
import br.com.compass.parking_service.services.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Vehicle", description = "Contains all the necessary operations related to resources for summarizing, " +
                                          "deleting, and updating vehicle.")

@RestController
@RequestMapping("api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    // Add a vehicle //
    @Operation(description = "Register a vehicle",
            summary = "Add a vehicle in database")
    @PostMapping
    public ResponseEntity<VehicleResponseDTO> addVehicle(@RequestBody VehicleRequestDTO vehicleRequestDTO) {
        VehicleResponseDTO vehicleResponse = vehicleService.save(vehicleRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleResponse);
    }

    // Get vehicle by plate //
    @Operation(description = "You can use the plate filter or return all registered vehicles",
            summary = "Consult a vehicle by plate")
    @GetMapping(params = "plate")
    public ResponseEntity<VehicleResponseDTO> getVehicleByPlate(
            @Parameter(description = "Search with plate", required = true)
            @RequestParam String plate) {
        VehicleResponseDTO vehicle = vehicleService.getVehicleByPlate(plate);
        return ResponseEntity.ok(vehicle);
    }

    // Delete a vehicle by id //

    @Operation(description = "Vehicle removal",
            summary = "Remove a vehicle")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(
            @Parameter(description = "ID of the vehicle to be deleted (NOT THE PLATE)", required = true)
            @PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }

    // Update a vehicle by id //

    @Operation(description = "Change a vehicle",
            summary = "Update a vehicle")
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateVehicle(
            @Parameter(description = "ID of the vehicle to be updated (NOT THE PLATE)", required = true)
            @PathVariable Long id,
            @RequestBody VehicleRequestDTO vehicleRequestDTO) {
        vehicleService.update(id, vehicleRequestDTO);
        return ResponseEntity.noContent().build();
    }
}
