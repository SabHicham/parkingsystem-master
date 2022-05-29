package com.parkit.parkingsystem.integration;

import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ParkingDataBaseIT {

    @Mock
    private static DataBaseTestConfig dataBaseTestConfig;
    private static ParkingSpotDAO parkingSpotDAO;
    private static TicketDAO ticketDAO;
    private static DataBasePrepareService dataBasePrepareService;

    @Mock
    private static InputReaderUtil inputReaderUtil;
    @Mock
    private static Connection con;
    @Mock
    private static PreparedStatement ps;
    @Mock
    private static ResultSet rs;

    @BeforeEach
    private void setUp() throws Exception{
        parkingSpotDAO = new ParkingSpotDAO();
        when(inputReaderUtil.readSelection()).thenReturn(1);
        when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
        //testParkingACaur()
        when(dataBaseTestConfig.getConnection()).thenReturn(con);
        when(con.prepareStatement(DBConstants.GET_TICKET)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getTimestamp(5)).thenReturn(Timestamp.valueOf(LocalDateTime.now().minusDays(1)));
        when(rs.getTimestamp(4)).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
        when(rs.getInt(1)).thenReturn(0);
        when(rs.getString(6)).thenReturn("CAR");
        parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
        ticketDAO = new TicketDAO();
        ticketDAO.dataBaseConfig = dataBaseTestConfig;
        dataBasePrepareService = new DataBasePrepareService();
    }

    @BeforeEach
    private void setUpPerTest() throws Exception {
    }

    @AfterAll
    private static void tearDown(){

    }

    @Test
    public void testParkingACar()throws Exception{
        //Given
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);

        //When
        parkingService.processIncomingVehicle();
        //then

        //TODO: check that a ticket is actualy saved in DB and Parking table is updated with availability
        Ticket ticket = ticketDAO.getTicket("ABCDEF");
        assertNotNull(ticket);
        assertEquals(false, ticket.getParkingSpot().isAvailable());
    }

    @Test
    public void testParkingLotExit()throws Exception{

        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        parkingService.processIncomingVehicle();
        parkingService.processExitingVehicle();
        //TODO: check that the fare generated and out time are populated correctly in the database
        String Matricule = inputReaderUtil.readVehicleRegistrationNumber();
        Ticket ticket = ticketDAO.getTicket(Matricule);
        assertNotNull(ticket.getOutTime());
        assertEquals(0,ticket.getPrice());
    }




}
