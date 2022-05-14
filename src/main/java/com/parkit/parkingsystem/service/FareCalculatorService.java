package com.parkit.parkingsystem.service;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

import java.time.Duration;

public class FareCalculatorService {

    private  TicketDAO ticketDAO;
    public FareCalculatorService(TicketDAO ticket){
        ticketDAO = ticket;
    }

    public void calculateFare(Ticket ticket){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().isBefore(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:");
        }
        //double duration = outTime - inTime;
        //Fonctionnalité 1 : gratuit si temps dans le parking <30 minutes
        long duration = Duration.between(ticket.getInTime(), ticket.getOutTime()).toMinutes();
        if (duration <= 30){
            duration = 0;
        }
        //TODO est-ce que l'utilisateur est deja venu
        //une réduction de 5% si l'utilisateur est déjà venu.
        //créer une méthode qui va envoyer soit 0.95 si l'utilisateur est déjà venue ou renvoyer 1
        double discount = checkDiscount(ticket.getVehicleRegNumber());

        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {

                    ticket.setPrice((duration * Fare.CAR_RATE_PER_MINUTE * discount));
                 break;
            }
            case BIKE: {
                ticket.setPrice(duration * Fare.BIKE_RATE_PER_MINUTE * discount);
                break;
            }
            default: throw new IllegalArgumentException("Unkown Parking Type");
        }
    }

    private double checkDiscount(String vehicleRegNumber) {
        if (ticketDAO.isCarClient(vehicleRegNumber)) {
            return 0.95;
        } else
            return 1;
    }
}