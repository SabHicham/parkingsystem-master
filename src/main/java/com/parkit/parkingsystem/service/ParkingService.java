package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.util.calendar.LocalGregorianCalendar;

import java.time.LocalDateTime;
import java.util.Date;

public class ParkingService {

    private static final Logger logger = LogManager.getLogger("ParkingService");

    private static FareCalculatorService fareCalculatorService;

    private InputReaderUtil inputReaderUtil;
    private ParkingSpotDAO parkingSpotDAO;
    private  TicketDAO ticketDAO;

    public ParkingService(InputReaderUtil inputReaderUtil, ParkingSpotDAO parkingSpotDAO, TicketDAO ticketDAO){
        this.inputReaderUtil = inputReaderUtil;
        this.parkingSpotDAO = parkingSpotDAO;
        this.ticketDAO = ticketDAO;
        fareCalculatorService = new FareCalculatorService(ticketDAO);
    }

    public void processIncomingVehicle() {
        try{
            ParkingSpot parkingSpot = getNextParkingNumberIfAvailable();
            if(parkingSpot !=null && parkingSpot.getId() > 0) {
                String vehicleRegNumber = getVehichleRegNumber();
                //Verifier si la voiture est déjà dans le parking
                //Ticket ticketDB   = ticketDAO.getTicket(vehicleRegNumber);

              /*if ((ticketDB.getVehicleRegNumber().equals(vehicleRegNumber )) &&
                      ( ticketDB.getOutTime()== null)){*/
                if(ticketDAO.isCarInside(vehicleRegNumber))
                {
                    System.out.println(" la plaque existe déja dans le parking  : " +vehicleRegNumber);
                    return;
                } else   {
                    parkingSpot.setAvailable(false);
                    parkingSpotDAO.updateParking(parkingSpot);//allot this parking space and mark it's availability as false

                    //Date inTime = new Date();
                    LocalDateTime inTime = LocalDateTime.now();

                    Ticket ticket = new Ticket();
                    //ID, PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME)
                    //ticket.setId(ticketID);
                    ticket.setParkingSpot(parkingSpot);
                    ticket.setVehicleRegNumber(vehicleRegNumber);
                    ticket.setPrice(0);
                    ticket.setInTime(inTime);
                    ticket.setOutTime(null);
                    ticketDAO.saveTicket(ticket);
                    System.out.println("Generated Ticket and saved in DB");
                    System.out.println("Please park your vehicle in spot number:"+parkingSpot.getId());
                    System.out.println("Recorded in-time for vehicle number:"+vehicleRegNumber+" is:"+inTime);
                    //ticketDAO.getTicket(vehicleRegNumber);
                }

            }
        }catch(Exception e){
            logger.error("Unable to process incoming vehicle",e);
        }
    }



    private String getVehichleRegNumber() throws Exception {
        System.out.println("Please type the vehicle registration number and press enter key");
        return inputReaderUtil.readVehicleRegistrationNumber();
    }

    public ParkingSpot getNextParkingNumberIfAvailable(){
        int parkingNumber=0;
        ParkingSpot parkingSpot = null;
        try{
            ParkingType parkingType = getVehichleType();
            parkingNumber = parkingSpotDAO.getNextAvailableSlot(parkingType);
            if(parkingNumber > 0){
                parkingSpot = new ParkingSpot(parkingNumber,parkingType, true);
            }else{
                throw new Exception("Error fetching parking number from DB. Parking slots might be full");
            }
        }catch(IllegalArgumentException ie){
            logger.error("Error parsing user input for type of vehicle", ie);
        }catch(Exception e){
            logger.error("Error fetching next available parking slot", e);
        }
        return parkingSpot;
    }

    private ParkingType getVehichleType(){
        System.out.println("Please select vehicle type from menu");
        System.out.println("1 CAR");
        System.out.println("2 BIKE");
        int input = inputReaderUtil.readSelection();
        switch(input){
            case 1: {
                return ParkingType.CAR;
            }
            case 2: {
                return ParkingType.BIKE;
            }
            default: {
                System.out.println("Incorrect input provided");
                throw new IllegalArgumentException("Entered input is invalid");
            }
        }
    }

    public void processExitingVehicle() {
        try{

            String vehicleRegNumber = getVehichleRegNumber();
            Ticket ticket = ticketDAO.getTicket(vehicleRegNumber);
            boolean isCarInside = ticketDAO.isCarInside(vehicleRegNumber);

            if ((!isCarInside)){
                System.out.println(" la plaque est déja partis, impossile de resaisir une sortie : " +ticket.getVehicleRegNumber());
                return;

            } else   {

                LocalDateTime outTime = LocalDateTime.now();
                ticket.setOutTime(outTime);
                fareCalculatorService.calculateFare(ticket);
                if(ticketDAO.updateTicket(ticket)) {
                    ParkingSpot parkingSpot = ticket.getParkingSpot();
                    parkingSpot.setAvailable(true);
                    parkingSpotDAO.updateParking(parkingSpot);
                        System.out.println("Please pay the parking fare:" + ticket.getPrice());
                        System.out.println("Recorded out-time for vehicle number:" + ticket.getVehicleRegNumber() + " is:" + outTime);
                    if (ticketDAO.isCarClient(vehicleRegNumber)) {
                        System.out.println("Bienvenue! En tant qu'utilisateur récurrent de notre parking, vous bénéficierez d'une réduction de 5%.");
                    }
                }else{
                    System.out.println("Unable to update ticket information. Error occurred");
                }}
        }catch(Exception exep){
            logger.error("Unable to process exiting vehicle",exep);
        }
    }
}
