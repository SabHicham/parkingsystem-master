package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

    public void calculateFare(Ticket ticket){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }

        int inHour = ticket.getInTime().getHours();
        int outHour = ticket.getOutTime().getHours();
        int inMinutes = ticket.getInTime().getMinutes();
        int outMinutes = ticket.getOutTime().getMinutes();
        int inDay = ticket.getInTime().getDay();
        int outDay = ticket.getOutTime().getDay();


        //TODO: Some tests are failing here. Need to check if this logic is correct

        double inTime = (inDay*24)+ inHour +((double)inMinutes/60);
        double outTime = (outDay*24)+ outHour +((double)outMinutes/60);

        double duration = outTime - inTime;


        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
                ticket.setPrice(duration * Fare.CAR_RATE_PER_HOUR);
                break;
            }
            case BIKE: {
                ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR);
                break;
            }
            default: throw new IllegalArgumentException("Unkown Parking Type");
        }
    }
}