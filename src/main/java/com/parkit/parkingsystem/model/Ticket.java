package com.parkit.parkingsystem.model;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class Ticket {
    private int id;
    private ParkingSpot parkingSpot;
    private String vehicleRegNumber;
    private double price;
    private LocalDateTime inTime;
    private LocalDateTime outTime;

    public Ticket(int id, ParkingSpot parkingSpot, String vehicleRegNumber, double price, LocalDateTime inTime, LocalDateTime outTime) {
        this.id = id;
        this.parkingSpot = parkingSpot;
        this.vehicleRegNumber = vehicleRegNumber;
        this.price = price;
        this.inTime = inTime;
        this.outTime = outTime;
    }
    public Ticket(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(ParkingSpot parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    public String getVehicleRegNumber() {
        return vehicleRegNumber;
    }

    public void setVehicleRegNumber(String vehicleRegNumber) {
        this.vehicleRegNumber = vehicleRegNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getInTime() {
        return inTime;
    }

    public void setInTime(LocalDateTime inTime) {
        this.inTime = inTime;
    }

    public LocalDateTime getOutTime() {
        return outTime;
    }

    public void setOutTime(LocalDateTime outTime) {
        this.outTime = outTime;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", parkingSpot=" + parkingSpot +
                ", vehicleRegNumber='" + vehicleRegNumber + '\'' +
                ", price=" + price +
                ", inTime=" + inTime +
                ", outTime=" + outTime +
                '}';
    }
}
