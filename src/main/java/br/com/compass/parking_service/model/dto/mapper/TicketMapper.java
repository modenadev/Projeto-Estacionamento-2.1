package br.com.compass.parking_service.model.dto.mapper;

import br.com.compass.parking_service.model.dto.TicketResponseExitDto;
import br.com.compass.parking_service.model.entity.Ticket;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TicketMapper {

    private final VehicleMapper vehicleMapper;

    public TicketMapper(VehicleMapper vehicleMapper) {
        this.vehicleMapper = vehicleMapper;
    }


    // Converte Ticket para TicketResponseExitDto
    public TicketResponseExitDto toResponseDto(Ticket ticket) {
        return new TicketResponseExitDto(
                ticket.getId(),ticket.getIdVehicle(),ticket.getGateEntry(),ticket.getEntryDate(),
                ticket.getGateExit(),ticket.getExitDate(),ticket.getParkingSpotList(), ticket.getPrice(), ticket.isParked());
    }

    public List<TicketResponseExitDto> toListDto(List<Ticket> tickets) {
        return tickets.stream().map(this::toResponseDto).toList();
    }

    public List<TicketResponseExitDto> toListDto(Optional<Ticket> tickets) {
        return tickets.stream().map(this::toResponseDto).toList();
    }
}
