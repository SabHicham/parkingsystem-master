package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InteractiveShell {

    private static final Logger logger = LogManager.getLogger("InteractiveShell");

    private static InteractiveShell instance = null;
    private InputReaderUtil inputReaderUtil;
    private ParkingSpotDAO parkingSpotDAO;
    private TicketDAO ticketDAO;
    private ParkingService parkingService;

    public InteractiveShell(InputReaderUtil inputReaderUtil, ParkingSpotDAO parkingSpotDAO, TicketDAO ticketDAO, ParkingService parkingService) {
        this.inputReaderUtil = inputReaderUtil;
        this.parkingSpotDAO = parkingSpotDAO;
        this.ticketDAO = ticketDAO;
        this.parkingService = parkingService;
        if (parkingService == null){
            this.parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        } else {
            this.parkingService = parkingService;
        }

    }

    public InteractiveShell() {
        this(new InputReaderUtil(), new ParkingSpotDAO(), new TicketDAO(), null);
    }

    public static InteractiveShell getInstance(){
        if (instance == null) {
            instance = new InteractiveShell();
        }
        return instance;
    }

    public static InteractiveShell getInstance(InputReaderUtil inputReaderUtil, ParkingSpotDAO parkingSpotDAO, TicketDAO ticketDAO, ParkingService parkingService){
        if (instance == null) {
            instance = new InteractiveShell(inputReaderUtil, parkingSpotDAO, ticketDAO, parkingService);
        }
        return instance;
    }

    public void loadInterface(){
        logger.info("App initialized!!!");
        System.out.println("Welcome to Parking System!");

        boolean continueApp = true;


        while(continueApp){
            loadMenu();
            int option = inputReaderUtil.readSelection();
            switch(option){
                case 1: {
                    parkingService.processIncomingVehicle();
                    break;
                }
                case 2: {
                    parkingService.processExitingVehicle();
                    break;
                }
                case 3: {
                    System.out.println("Exiting from the system!");
                    continueApp = false;
                    break;
                }
                default: System.out.println("Unsupported option. Please enter a number corresponding to the provided menu");
            }
        }
    }

    private static void loadMenu(){
        System.out.println("Please select an option. Simply enter the number to choose an action");
        System.out.println("1 New Vehicle Entering - Allocate Parking Space");
        System.out.println("2 Vehicle Exiting - Generate Ticket Price");
        System.out.println("3 Shutdown System");
    }

}
