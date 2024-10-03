package br.com.compass.parking_service.model.dto;

public class ParkingSpotSummaryDto {
    private int occupied_SingleSpots;
    private int singleSpots_Capacity;
    private int occupied_MonthlySpots;
    private int monthlySpots_Capacity;

    public ParkingSpotSummaryDto() {}

    public ParkingSpotSummaryDto(int occupied_SingleSpots, int singleSpots_Capacity, int occupied_MonthlySpots, int monthlySpots_Capacity) {
        this.occupied_SingleSpots = occupied_SingleSpots;
        this.singleSpots_Capacity = singleSpots_Capacity;
        this.occupied_MonthlySpots = occupied_MonthlySpots;
        this.monthlySpots_Capacity = monthlySpots_Capacity;
    }

    public int getOccupied_SingleSpots() {
        return occupied_SingleSpots;
    }

    public void setOccupied_SingleSpots(int occupied_SingleSpots) {
        this.occupied_SingleSpots = occupied_SingleSpots;
    }

    public int getSingleSpots_Capacity() {
        return singleSpots_Capacity;
    }

    public void setSingleSpots_Capacity(int singleSpots_Capacity) {
        this.singleSpots_Capacity = singleSpots_Capacity;
    }

    public int getOccupied_MonthlySpots() {
        return occupied_MonthlySpots;
    }

    public void setOccupied_MonthlySpots(int occupied_MonthlySpots) {
        this.occupied_MonthlySpots = occupied_MonthlySpots;
    }

    public int getMonthlySpots_Capacity() {
        return monthlySpots_Capacity;
    }

    public void setMonthlySpots_Capacity(int monthlySpots_Capacity) {
        this.monthlySpots_Capacity = monthlySpots_Capacity;
    }
}
