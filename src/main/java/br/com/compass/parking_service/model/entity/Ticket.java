package br.com.compass.parking_service.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "ticket")
public class Ticket implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vehicle", nullable = false)
    private Vehicle vehicle;

    @Column(name = "gate_entry")
    private int gateEntry;

    @Column(name = "entry_date")
    private LocalDateTime entryDate;

    @Column(name = "gate_exit")
    private int gateExit;

    @Column(name = "exit_date")
    private LocalDateTime exitDate;

    @Column(name = "price")
    private BigDecimal price;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "ticket_id", referencedColumnName = "id")
    private List<ParkingSpot> parkingSpotList = new ArrayList<>();

    @Column(name = "parked")
    private boolean parked;

    public Ticket(){}

    public Ticket(Long id, Vehicle vehicle, int gateEntry, LocalDateTime entryDate, int gateExit, LocalDateTime exitDate, List<ParkingSpot> parkingSpotList, BigDecimal price, boolean parked) {
        this.id = id;
        this.vehicle = vehicle;
        this.gateEntry = gateEntry;
        this.entryDate = entryDate;
        this.gateExit = gateExit;
        this.exitDate = exitDate;
        this.parkingSpotList = parkingSpotList;
        this.price = price;
        this.parked = parked;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vehicle getIdVehicle() {
        return vehicle;
    }

    public void setIdVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
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

    public List<ParkingSpot> getParkingSpotList() {
        return parkingSpotList;
    }

    public void setParkingSpotList(List<ParkingSpot> vacancyNumber) {
        this.parkingSpotList = vacancyNumber;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return id.equals(ticket.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ticket{" +
                "id=" + id +
                '}';
    }

}
