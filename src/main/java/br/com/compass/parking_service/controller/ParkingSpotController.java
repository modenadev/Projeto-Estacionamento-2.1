package br.com.compass.parking_service.controller;

import br.com.compass.parking_service.model.dto.ParkingSpotResponseDto;
import br.com.compass.parking_service.model.dto.ParkingSpotSummaryDto;
import br.com.compass.parking_service.model.dto.ParkingSpotUpdateDto;
import br.com.compass.parking_service.services.ParkingSpotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "ParkingSpots", description = "Contains all the necessary operations related to resources for summarizing, " +
                                          "deleting, and updating parking spots.")
@RestController
@RequestMapping("/api/parkingspot")
public class ParkingSpotController {

    private final ParkingSpotService parkingSpotService;

    public ParkingSpotController(ParkingSpotService parkingSpotService){
        this.parkingSpotService = parkingSpotService;
    }

    @Operation(summary = "Presents the list of all available and occupied parking spots.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok->Parking spots and capacity.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ParkingSpotSummaryDto.class)))
            })
    @GetMapping
    public ResponseEntity<ParkingSpotSummaryDto> getParkingSummary() {
        ParkingSpotSummaryDto summary = parkingSpotService.getParkingSummary();
        return ResponseEntity.ok(summary);
    }


    @Operation(summary = "Delete a parking spot by ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "No Content: The parking spot was successfully deleted."),
                    @ApiResponse(responseCode = "404", description = "Not Found: Parking spot not found.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request: Invalid ID provided.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error: Unexpected error occurred.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParkingSpot(@PathVariable Long id) {
        parkingSpotService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Change the parking capacity.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "No Content-> Successfully changed."),
                    @ApiResponse(responseCode = "400", description = "Bad Request.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class),
                                    examples = @ExampleObject(value = "{\"Error\": \"The requested capacity is invalid.\"}"))),
                    @ApiResponse(responseCode = "409", description = "Conflict.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class),
                                    examples = @ExampleObject(value = "{\"Error\": \"Capacity less than allowed.\"}")))
            })
    @PatchMapping
    public ResponseEntity<Void> updateParkingSpot(@Valid @RequestBody ParkingSpotUpdateDto updateDto) {
        parkingSpotService.updateParkingSpot(updateDto);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Presents the list of all available parking spots.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok -> List of free parking spots returned.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ParkingSpotResponseDto.class))),
                    @ApiResponse(responseCode = "204", description = "No Content -> No free parking spots available."),
                    @ApiResponse(responseCode = "404", description = "Not Found -> No free parking spots found."),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error -> An unexpected error occurred.")
            }
    )
    @GetMapping("/allFreeSpots")
    public ResponseEntity<List<ParkingSpotResponseDto>> findAllFreeParkingSpots(){
        return ResponseEntity.ok(parkingSpotService.findByVacancyOccupiedFalse());
    }

    @GetMapping("/occupied")
    public ResponseEntity<List<ParkingSpotResponseDto>> findByVacancyOccupiedTrue(){
        return ResponseEntity.ok(parkingSpotService.findByVacancyOccupiedTrue());
    }

}
