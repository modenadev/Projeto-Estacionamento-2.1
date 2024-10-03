package br.com.compass.parking_service.services;

import br.com.compass.parking_service.exceptions.ConflictException;
import br.com.compass.parking_service.model.dto.ParkingSpotSummaryDto;
import br.com.compass.parking_service.model.dto.ParkingSpotResponseDto;
import br.com.compass.parking_service.model.dto.ParkingSpotUpdateDto;
import br.com.compass.parking_service.model.dto.mapper.ParkingSpotMapper;
import br.com.compass.parking_service.model.entity.ParkingSpot;
import br.com.compass.parking_service.repository.ParkingSpotRepository;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingSpotService {

    private final ParkingSpotRepository parkingSpotRepository;

    public ParkingSpotService(ParkingSpotRepository parkingSportRepository) {
        this.parkingSpotRepository = parkingSportRepository;
    }

    @Transactional
    public List<ParkingSpotResponseDto> findByVacancyOccupiedFalse() {
        List<ParkingSpot> parkingSpots = parkingSpotRepository.findByVacancy_OccupiedFalse();
        return ParkingSpotMapper.toDtoList(parkingSpots);
    }

    @Transactional
    public List<ParkingSpotResponseDto> findByVacancyOccupiedTrue() {
        List<ParkingSpot> parkingSpots = parkingSpotRepository.findByVacancy_OccupiedTrue();
        return ParkingSpotMapper.toDtoList(parkingSpots);
    }


    @Transactional
    public void deleteById(Long id){
        parkingSpotRepository.deleteById(id);
    }

    @Transactional
    public void updateParkingSpot(ParkingSpotUpdateDto updateDto){

        Long monthly_spots = updateDto.getMonthly_capacity();
        Long general_spots = updateDto.getGeneral_capacity();
        long total_spots = monthly_spots + general_spots;
        int current_spots = parkingSpotRepository.findAll().size();

        if(findByVacancyOccupiedTrue().size() > total_spots) {
            throw new ConflictException("The number of spots selected is inferior to the amount of cars parked.");
        }

        if(current_spots < total_spots) {
            int difference = (int) (total_spots - current_spots);

            for(int i=0; i<difference;i++) {
                parkingSpotRepository.save(new ParkingSpot(false, false));
            }
        }

        parkingSpotRepository.updateMonthlySpots(0L, monthly_spots);
        parkingSpotRepository.updateGeneralSpots(monthly_spots+1, total_spots);
        parkingSpotRepository.deactivateActiveVacancy(total_spots);

    }

    @Transactional
    public ParkingSpotSummaryDto getParkingSummary() {

        int occupiedSingle = parkingSpotRepository.countByVacancyOccupiedAndReserved(true, false);
        int monthlyOccupied = parkingSpotRepository.countByVacancyOccupiedAndReserved(true, true);

        int singleCapacity = parkingSpotRepository.countByReserved(false);
        int monthlyCapacity = parkingSpotRepository.countByReserved(true);

        ParkingSpotSummaryDto dto = new ParkingSpotSummaryDto(occupiedSingle, singleCapacity,
                monthlyOccupied, monthlyCapacity);
        return dto;
    }

}
