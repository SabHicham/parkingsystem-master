package com.parkit.parkingsystem.service;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

import java.time.Duration;

public class FareCalculatorService {

    private  TicketDAO ticketDAO;

    public void calculateFare(Ticket ticket){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().isBefore(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:");
        }
        //double duration = outTime - inTime;
        //recuperer les ,inutes dans le parking
        long duration;
        if ((Duration.between(ticket.getInTime(), ticket.getOutTime()).toMinutes()) <= 30) {
            duration = 0;
                 } else {
                    duration = Duration.between(ticket.getInTime(), ticket.getOutTime()).toMinutes() - 30  ;
             }
        //TODO est-ce que l'utilisateur est deja venu

        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
                if(ticketDAO.isCarClient(ticket.getVehicleRegNumber())){
                    ticket.setPrice((duration * Fare.CAR_RATE_PER_HOUR)- 0.05 * (duration * Fare.CAR_RATE_PER_HOUR));
                }else {
                ticket.setPrice(duration * Fare.CAR_RATE_PER_HOUR);
              }   break;}

            case BIKE: {
                ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR);
                break;
            }
            default: throw new IllegalArgumentException("Unkown Parking Type");
        }
    }
}