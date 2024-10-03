package br.com.compass.parking_service.model.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "parking_spot")
public class ParkingSpot implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "reserved", nullable = false)
    private Boolean reserved;
    @Column(name = "vacancy_occupied", nullable = false)
    private Boolean vacancy_occupied;

    //Creating a manyToOne relation with the FK
    @ManyToOne
    @JoinColumn(name = "occupied_by", referencedColumnName = "id", nullable = true)
    private Vehicle occupied_by;

    @Column(name = "active_vacancy")
    private Boolean active_vacancy;


    @Column(name = "ticket_id")
    private Long ticketId;


    public ParkingSpot(){}

    public ParkingSpot(Boolean reserved, Boolean vacancy_occupied){
        this.reserved = reserved;
        this.vacancy_occupied = vacancy_occupied;

    }

    public ParkingSpot(Long id, Vehicle occupiedBy, Boolean vacancyOccupied, Boolean reserved) {
        this.id = id;
        this.occupied_by = occupiedBy;
        this.vacancy_occupied = vacancyOccupied;
        this.reserved = reserved;
    }

    //Generating Getters
    public Long getId() {
        return id;
    }

    public Vehicle getOccupiedBy() {
        return occupied_by;
    }

    public boolean getVacancyOccupied() {
        return vacancy_occupied;
    }

    public void setVacancy_occupied(Boolean vacancy_occupied) {
        this.vacancy_occupied = vacancy_occupied;
    }

    public Boolean getReserved() {
        return reserved;
    }

    public Boolean getActiveVacancy() {
        return active_vacancy;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public Boolean getVacancy_occupied() {
        return vacancy_occupied;
    }

    public Vehicle getOccupied_by() {
        return occupied_by;
    }

    public Boolean getActive_vacancy() {
        return active_vacancy;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSpot that = (ParkingSpot) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}