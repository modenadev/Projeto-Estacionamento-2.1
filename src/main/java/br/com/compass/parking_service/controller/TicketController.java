package br.com.compass.parking_service.controller;


import br.com.compass.parking_service.model.dto.TicketResponseExitDto;
import br.com.compass.parking_service.model.dto.TicketCreateDto;
import br.com.compass.parking_service.model.dto.TicketResponseExitDto;
import br.com.compass.parking_service.model.dto.mapper.TicketMapper;
import br.com.compass.parking_service.model.entity.Ticket;
import br.com.compass.parking_service.model.enums.FilterTypeEnum;
import br.com.compass.parking_service.services.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Ticket", description = "Contains all the necessary operations related to resources for summarizing, " +
                                          "deleting, and updating ticket.")
@RestController
@RequestMapping(value = "/tickets")
public class TicketController {

    private TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @Operation(summary = "Create a ticket based on an existing card",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok->Created ticket.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TicketResponseExitDto.class))),
                    @ApiResponse(responseCode = "404", description = "Not Found: No vehicle was found with this plate.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Error->Ticket not created.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TicketResponseExitDto.class)))
            })
    @PostMapping
    public ResponseEntity<TicketResponseExitDto> create (@RequestBody TicketCreateDto objDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketService.insert(objDto));
    }

    @Operation(summary = "It has all possible ticket searches, with or without parameters.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok->Ticket Found.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TicketResponseExitDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request: Invalid ID provided.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Not Found: Ticket not found with this param.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error: Unexpected error occurred.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)))
            })
    @GetMapping()
    public ResponseEntity<List<TicketResponseExitDto>> getByFilter(@RequestParam(required = false) FilterTypeEnum filter, @RequestParam(required = false) String value) {
        var ticket = ticketService.getByFilter(filter, value);
        return ResponseEntity.ok(ticket);
    }

    @Operation(summary = "Delete a ticket by ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "No Content: The ticket was successfully deleted."),
                    @ApiResponse(responseCode = "404", description = "Not Found: Ticket not found.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request: Invalid ID provided.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error: Unexpected error occurred.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)))
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ticketService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Generating the exit of a vehicle through a ticket",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok->Updated ticket.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TicketResponseExitDto.class))),
                    @ApiResponse(responseCode = "404", description = "Not Found: Ticket not found.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request: Invalid ID provided.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error: Unexpected error occurred.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PatchMapping("/{id}/exit")
    public ResponseEntity<TicketResponseExitDto> update(@PathVariable Long id) {
        return ResponseEntity.ok().body(ticketService.update(id));
    }


}
