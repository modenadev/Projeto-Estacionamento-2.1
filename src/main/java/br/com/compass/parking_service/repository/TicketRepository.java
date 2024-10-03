package br.com.compass.parking_service.repository;

import br.com.compass.parking_service.model.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("SELECT t from Ticket t WHERE t.vehicle.plate = :plate")
    List<Ticket> findByPlate(String plate);

    // Ajuste na query para buscar todos os tickets relacionados à vaga de estacionamento
    @Query("SELECT t FROM Ticket t JOIN t.parkingSpotList p WHERE p.id = :parkingSpotNumber")
    List<Ticket> findAllByParkingSpot(@Param("parkingSpotNumber") String parkingSpotNumber);
}
