package br.com.compass.parking_service.repository;

import br.com.compass.parking_service.model.dto.VehicleResponseDTO;
import br.com.compass.parking_service.model.entity.ParkingSpot;
import br.com.compass.parking_service.model.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {
    @Query("SELECT p FROM ParkingSpot p WHERE p.vacancy_occupied = false")
    List<ParkingSpot> findByVacancy_OccupiedFalse();

    @Query("SELECT p FROM ParkingSpot p WHERE p.vacancy_occupied = true")
    List<ParkingSpot> findByVacancy_OccupiedTrue();

    @Modifying
    @Query("UPDATE ParkingSpot p SET p.vacancy_occupied = true WHERE p IN :parkingSpots")
    void UpdateVacancy_IsOccupied(@Param("parkingSpots") List<ParkingSpot> parkingSpots);

    @Modifying
    @Query("UPDATE ParkingSpot p SET p.vacancy_occupied = false WHERE p IN :parkingSpots")
    void UpdateVacancy_IsNotOccupied(@Param("parkingSpots") List<ParkingSpot> parkingSpots);

    @Modifying
    @Query("UPDATE ParkingSpot p SET p.occupied_by = :vehicle WHERE p IN :parkingSpots")
    void UpdateVacancy_OccupiedBy(@Param("parkingSpots") List<ParkingSpot> parkingSpots, @Param("vehicle") Vehicle vehicle);

    @Modifying
    @Query("UPDATE ParkingSpot p SET p.occupied_by = null WHERE p IN :parkingSpots AND p.occupied_by = :vehicle")
    void UpdateVacancy_NotOccupiedBy(@Param("parkingSpots") List<ParkingSpot> parkingSpots, @Param("vehicle") Vehicle vehicle);

    @Query("SELECT COUNT(p) FROM ParkingSpot p WHERE p.vacancy_occupied = :occupied AND p.reserved = :reserved AND p.active_vacancy = true")
    int countByVacancyOccupiedAndReserved(@Param("occupied") boolean occupied, @Param("reserved") boolean reserved);

    @Query("SELECT COUNT(p) FROM ParkingSpot p WHERE p.reserved = :reserved AND p.active_vacancy = true")
    int countByReserved(@Param("reserved") boolean reserved);

    List<ParkingSpot> findByIdBetween(Long x, Long y);

    @Modifying
    @Query("UPDATE ParkingSpot p SET p.reserved = true, p.active_vacancy = true WHERE p.id BETWEEN :start AND :end")
    void updateMonthlySpots(@Param("start") Long start, @Param("end") Long end);

    @Modifying
    @Query("UPDATE ParkingSpot p SET p.reserved = false, p.active_vacancy = true WHERE p.id BETWEEN :start AND :end")
    void updateGeneralSpots(@Param("start") Long start, @Param("end") Long end);

    @Modifying
    @Query("UPDATE ParkingSpot p SET p.active_vacancy = false WHERE p.id > :threshold")
    void deactivateActiveVacancy(@Param("threshold") Long threshold);


}
