package br.com.compass.parking_service.services;

import br.com.compass.parking_service.exceptions.*;
import br.com.compass.parking_service.model.dto.TicketCreateDto;
import br.com.compass.parking_service.model.dto.TicketResponseExitDto;
import br.com.compass.parking_service.model.dto.VehicleResponseDTO;
import br.com.compass.parking_service.model.dto.mapper.TicketMapper;
import br.com.compass.parking_service.model.entity.*;
import br.com.compass.parking_service.model.enums.FilterTypeEnum;
import br.com.compass.parking_service.repository.ParkingSpotRepository;
import br.com.compass.parking_service.repository.TicketRepository;
import br.com.compass.parking_service.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;


@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final VehicleRepository vehicleRepository;
    private final ParkingSpotRepository parkingSpotRepository;
    private final TicketMapper mapper;

    public TicketService(TicketRepository ticketRepository, VehicleRepository vehicleRepository, ParkingSpotRepository parkingSpotRepository, TicketMapper mapper) {
        this.ticketRepository = ticketRepository;
        this.vehicleRepository = vehicleRepository;
        this.parkingSpotRepository = parkingSpotRepository;
        this.mapper = mapper;
    }
    @Transactional
    public TicketResponseExitDto insert(TicketCreateDto dto) {

        try {
            String plate = dto.getPlate();
            TypeVehicle type = dto.getTypeVehicle();
            Vehicle vehicle = vehicleRepository.findByPlate(plate).orElseThrow(
                    () -> new NotFoundExcepetion("Vehicle not found, by plate " + plate));
            if (type != vehicle.getTypeVehicle()){
                throw new TypeVehicleException("Tipo de vehicle digitado não corresponde " +
                                               "ao tipo correspondente a placa. Tipo correto para esta placa: "
                                               + vehicle.getTypeVehicle() );
            }
            List<ParkingSpot> parkingSpots = parkingSpotRepository.findByVacancy_OccupiedFalse();
            Random random = new Random();
            int gateEntry;
            List<ParkingSpot> parkingSpotsToAdd;

            switch (type) {
                case MOTORCYCLE:
                    gateEntry = 5;
                    parkingSpotsToAdd = getAvailableSpotsForMotorcycles(parkingSpots, vehicle.getCategory());
                    break;
                case PASSENGER_CAR:
                    gateEntry = random.nextInt(1, 5);
                    parkingSpotsToAdd = getAvailableSpotsForBigVehicles(parkingSpots, vehicle.getCategory(), type.getValue());
                    break;
                case DELIVERY_TRUCK:
                    gateEntry = 1;
                    parkingSpotsToAdd = getAvailableSpotsForBigVehicles(parkingSpots, vehicle.getCategory(), type.getValue());
                    break;
                case  PUBLIC_SERVICE:
                    gateEntry = random.nextInt(1, 5);
                    parkingSpotsToAdd = new ArrayList<>();
                    break;
                default:
                    throw new GateInvalidException("Error: Type not valid");
            }
            parkingSpotRepository.UpdateVacancy_IsOccupied(parkingSpotsToAdd);
            parkingSpotRepository.UpdateVacancy_OccupiedBy(parkingSpotsToAdd,vehicle);
            vehicle.setParkingSpots(parkingSpotsToAdd);
            Ticket ticket = new Ticket();
            ticket.setIdVehicle(vehicle);
            ticket.setEntryDate(LocalDateTime.now());
            ticket.setGateEntry(gateEntry);
            ticket.setParked(true);
            ticket.setParkingSpotList(vehicle.getParkingSpots());

            return mapper.toResponseDto(ticketRepository.save(ticket));

        } catch (TicketCreateException ex) {
            throw new TicketCreateException("Error: error creating the ticket");
        }
    }

    public List<TicketResponseExitDto> getByFilter(FilterTypeEnum filter, String value) {
        if (filter == null) {
            return  mapper.toListDto(ticketRepository.findAll());
        }
        List<TicketResponseExitDto> response = new ArrayList<>();
        try {
            switch (filter) {
                case ID -> response = mapper.toListDto(ticketRepository.findById(Long.parseLong(value)));
                case PLATE -> response = mapper.toListDto(ticketRepository.findByPlate(value));
                case PARKING_SPOT -> response = mapper.toListDto(ticketRepository.findAllByParkingSpot(value));
            }
            return response;
        } catch (Exception ex) {
            throw new TicketNotFoundException("Error: Unable to find ticket based on this information");
        }

    }

    public void delete(Long id) {
        ticketRepository.deleteById(id);
    }

    private Ticket getById(Long id) {
        return ticketRepository.findById(id).orElseThrow(
                () -> new TicketNotFoundException("Error: Unable to find ticket based on this id" + id)
        );
    }

    @Transactional
    public TicketResponseExitDto update(Long id) {
        Ticket ticket = getById(id);
        Vehicle vehicle = ticket.getIdVehicle();
        TypeVehicle type = ticket.getIdVehicle().getTypeVehicle();
        Random random = new Random();
        int gateExit;

        switch (type) {
            case MOTORCYCLE:
                gateExit = 10;
                break;
            case PASSENGER_CAR, PUBLIC_SERVICE, DELIVERY_TRUCK:
                gateExit = random.nextInt(6, 10);
                break;
            default:
                throw new TypeVehicleException("Error: Type not valid");
        }
        if (Objects.equals(ticket.getIdVehicle().getCategory(), Category.CASUAL)) {
            ticket.setExitDate(LocalDateTime.now());
            var time = Duration.between(ticket.getExitDate(), ticket.getEntryDate()).toMinutes();
            BigDecimal amount = BigDecimal.valueOf(0.10).multiply(BigDecimal.valueOf(time));
            if (amount.doubleValue() <= 5.0) {
                amount = BigDecimal.valueOf(5.0);
            }
            ticket.setPrice(amount.setScale(2, RoundingMode.HALF_UP));
        } else {
            ticket.setPrice(null);
        }
        parkingSpotRepository.UpdateVacancy_IsNotOccupied(ticket.getParkingSpotList());
            parkingSpotRepository.UpdateVacancy_NotOccupiedBy(ticket.getParkingSpotList(),vehicle);
        ticket.setGateExit(gateExit);
        ticket.setParked(false);
        return mapper.toResponseDto(ticketRepository.save(ticket));
    }

    private List<ParkingSpot> getAvailableSpotsForBigVehicles(List<ParkingSpot> parkingSpots, Category category, int size) {
        List<ParkingSpot> availableSpots = new ArrayList<>();
        int count = 0;

        if (category.equals(Category.CASUAL)) {
            parkingSpots = parkingSpots
                    .stream()
                    .filter(parkingSpot -> parkingSpot.getReserved().equals(false))
                    .toList();
        }

        for (int i = 0; i < parkingSpots.size(); i++) {
            if (i == 0) {
                count++;
                availableSpots.add(parkingSpots.get(i));
            }
            if (i > 0 && parkingSpots.get(i).getId() - parkingSpots.get(i - 1).getId() == 1) {
                if (count == 0) {
                    availableSpots.add(parkingSpots.get(i - 1));
                }
                count++;
                availableSpots.add(parkingSpots.get(i));
            } else if (i > 0 && parkingSpots.get(i).getId() - parkingSpots.get(i - 1).getId() != 1){
                availableSpots.clear();
                count = 0;
            }
            if (size == count){
                return availableSpots;
            }

        }
        if (availableSpots.isEmpty()) {
            throw new ParkingSpotNullException("Error: Unable to create ticket as there are no spaces available for this vehicle category");
        }
        return availableSpots;
    }

    private List<ParkingSpot> getAvailableSpotsForMotorcycles(List<ParkingSpot> parkingSpots, Category category) {

        if (category.equals(Category.CASUAL)) {
            parkingSpots = parkingSpots
                    .stream()
                    .filter(parkingSpot -> parkingSpot.getReserved().equals(false))
                    .toList();
        }
        return List.of(parkingSpots.stream().findFirst().orElseThrow(
                () -> new ParkingSpotNullException("Error: Unable to create ticket as there are no spaces available for this vehicle category")
        ));
    }
}
