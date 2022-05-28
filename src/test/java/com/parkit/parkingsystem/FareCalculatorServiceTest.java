package com.parkit.parkingsystem;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.FareCalculatorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Date;
@ExtendWith(MockitoExtension.class)
public class FareCalculatorServiceTest {

    private static FareCalculatorService fareCalculatorService;
    private Ticket ticket;
    @Mock
    private static TicketDAO ticketDAO;

    @BeforeEach
    private void setUp() {
        fareCalculatorService = new FareCalculatorService(ticketDAO);
    }

    @BeforeEach
    private void setUpPerTest() {
        ticket = new Ticket();
    }

    @Test
    public void calculateFareCar(){
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);

        ticket.setInTime(LocalDateTime.now());
        ticket.setOutTime(LocalDateTime.now().plusHours(1));
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals(Fare.CAR_RATE_PER_HOUR,ticket.getPrice());
    }

    @Test
    public void calculateFareBike(){

        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);

        ticket.setInTime(LocalDateTime.now());
        ticket.setOutTime(LocalDateTime.now().plusHours(1));
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals(Fare.BIKE_RATE_PER_HOUR, ticket.getPrice());
    }

    @Test
    public void calculateFareUnkownType(){

        ParkingSpot parkingSpot = new ParkingSpot(1, null,false);

        ticket.setInTime(LocalDateTime.now());
        ticket.setOutTime(LocalDateTime.now());
        ticket.setParkingSpot(parkingSpot);
        assertThrows(NullPointerException.class, () -> fareCalculatorService.calculateFare(ticket));
    }

    @Test
    public void calculateFareBikeWithFutureInTime(){

        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);

        ticket.setInTime(LocalDateTime.now());
        ticket.setOutTime(LocalDateTime.now().minusMinutes(10));
        ticket.setParkingSpot(parkingSpot);
        assertThrows(IllegalArgumentException.class, () -> fareCalculatorService.calculateFare(ticket));
    }

    @Test
    public void calculateFareBikeWithLessThanOneHourParkingTime(){

        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);

        ticket.setInTime(LocalDateTime.now());
        ticket.setOutTime(LocalDateTime.now().plusMinutes(45));
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals((0.75 * Fare.BIKE_RATE_PER_HOUR), ticket.getPrice() );
    }

    @Test
    public void calculateFareCarWithLessThanOneHourParkingTime(){

        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);

        ticket.setInTime(LocalDateTime.now());
        ticket.setOutTime(LocalDateTime.now().plusMinutes(45));
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals( (0.75 * Fare.CAR_RATE_PER_HOUR) , ticket.getPrice());
    }

    @Test
    public void calculateFareCarWithMoreThanADayParkingTime(){

        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);

        ticket.setInTime(LocalDateTime.now());
        ticket.setOutTime(LocalDateTime.now().plusDays(1));
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals( (24 * Fare.CAR_RATE_PER_HOUR) , ticket.getPrice());
    }
    @Test
    public void calculateFareCarWithDiscount(){
        when(ticketDAO.isCarClient(any())).thenReturn(true);

        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);

        ticket.setInTime(LocalDateTime.now());
        ticket.setOutTime(LocalDateTime.now().plusDays(1));
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals( (24 * Fare.CAR_RATE_PER_HOUR)*0.95 , ticket.getPrice());
    }
    @Test
    public void calculateFareCarWithParkingNull(){
        when(ticketDAO.isCarClient(any())).thenReturn(true);

        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.UKNOWN,false);
        ticket.setInTime(LocalDateTime.now());
        ticket.setOutTime(LocalDateTime.now().plusDays(1));
        ticket.setParkingSpot(parkingSpot);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            fareCalculatorService.calculateFare(ticket);
        });
    }
}
