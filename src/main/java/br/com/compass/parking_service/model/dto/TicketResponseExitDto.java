package br.com.compass.parking_service.model.dto;

import br.com.compass.parking_service.model.entity.ParkingSpot;
import br.com.compass.parking_service.model.entity.Vehicle;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class TicketResponseExitDto {

    @NotBlank
    private Long id;

    private VehicleResponseDTO idVehicle;

    @NotBlank
    private int gateEntry;

    @NotBlank
    private LocalDateTime entryDate;

    @NotBlank
    private int gateExit;

    @NotBlank
    private LocalDateTime exitDate;

    private List<Long> parkingSpotList;

    private BigDecimal price;

    @NotBlank
    private boolean parked;

    public TicketResponseExitDto() {
    }

    public TicketResponseExitDto(Long id, Vehicle vehicle, int gateEntry, LocalDateTime entryDate, int gateExit, LocalDateTime exitDate, List<ParkingSpot> parkingSpotList, BigDecimal price, boolean parked) {
        this.id = id;
        this.idVehicle = new VehicleResponseDTO(vehicle.getId(), vehicle.getPlate(), vehicle.getCategory().toString(), vehicle.getTypeVehicle().toString(), vehicle.getActive());
        this.gateEntry = gateEntry;
        this.entryDate = entryDate;
        this.gateExit = gateExit;
        this.exitDate = exitDate;
        this.parkingSpotList = parkingSpotList.stream().map(ParkingSpot::getId).toList();
        this.price = price;
        this.parked = parked;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VehicleResponseDTO getIdVehicle() {
        return idVehicle;
    }

    public void setIdVehicle(VehicleResponseDTO idVehicle) {
        this.idVehicle = idVehicle;
    }

    public int getGateEntry() {
        return gateEntry;
    }

    public void setGateEntry(int gateEntry) {
        this.gateEntry = gateEntry;
    }

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
    }

    public int getGateExit() {
        return gateExit;
    }

    public void setGateExit(int gateExit) {
        this.gateExit = gateExit;
    }

    public LocalDateTime getExitDate() {
        return exitDate;
    }

    public void setExitDate(LocalDateTime exitDate) {
        this.exitDate = exitDate;
    }

    public List<Long> getparkingSpotList() {
        return parkingSpotList;
    }

    public void setparkingSpotList(List<Long> parkingSpotList) {
        this.parkingSpotList = parkingSpotList;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isParked() {
        return parked;
    }

    public void setParked(boolean parked) {
        this.parked = parked;
    }

    @Override
    public String toString() {
        return "TicketResponseExitDto{" +
               "id=" + id +
               ", idVehicle=" + idVehicle +
               ", gateEntry=" + gateEntry +
               ", entryDate=" + entryDate +
               ", gateExit=" + gateExit +
               ", exitDate=" + exitDate +
               ", parkingSpotList='" + parkingSpotList + '\'' +
               ", price=" + price +
               '}';
    }

}
