package br.com.compass.parking_service.model.dto.mapper;

import br.com.compass.parking_service.model.dto.ParkingSpotCreateDto;
import br.com.compass.parking_service.model.dto.ParkingSpotResponseDto;
import br.com.compass.parking_service.model.dto.ParkingSpotUpdateDto;
import br.com.compass.parking_service.model.entity.ParkingSpot;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

public class ParkingSpotMapper {

    //
    private static ModelMapper modelMapper = new ModelMapper();

//    public ParkingSpotMapper(ModelMapper modelMapper){
//        this.modelMapper = modelMapper;
//    }

    public static ParkingSpotCreateDto toCreateDto(ParkingSpot parkingSpot) {
        return modelMapper.map(parkingSpot, ParkingSpotCreateDto.class);
    }

    public static ParkingSpotUpdateDto toUpdateDto(ParkingSpot parkingSpot) {
        return modelMapper.map(parkingSpot, ParkingSpotUpdateDto.class);
    }

    public static ParkingSpot toEntity(ParkingSpotCreateDto parkingSpotCreateDto) {
        return modelMapper.map(parkingSpotCreateDto, ParkingSpot.class);
    }

    public static ParkingSpotResponseDto toResponseDto(ParkingSpot parkingSpot) {
        return modelMapper.map(parkingSpot, ParkingSpotResponseDto.class);
    }

    public static List<ParkingSpotResponseDto> toDtoList(List<ParkingSpot> parkingSpotList) {
        return parkingSpotList.stream().map(parkingSpot -> modelMapper.map(parkingSpot, ParkingSpotResponseDto.class))
                .collect(Collectors.toList());
    }
}
