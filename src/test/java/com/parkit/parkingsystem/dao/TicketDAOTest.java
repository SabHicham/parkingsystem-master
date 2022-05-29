package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;

import com.parkit.parkingsystem.service.ParkingService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TicketDAOTest {

    @Mock
    private static DataBaseTestConfig dataBaseTestConfig;
    @Mock
    private static Connection con;
    @Mock
    private static PreparedStatement ps;
    private static DataBasePrepareService dataBasePrepareService;
    private static TicketDAO ticketDAO;
    @Mock
    private static ResultSet rs;



    @BeforeEach
    private void setUp() {
        ticketDAO = new TicketDAO();
        ticketDAO.dataBaseConfig = dataBaseTestConfig;
        dataBasePrepareService = new DataBasePrepareService();
        dataBasePrepareService.clearDataBaseEntries();

    }



    @Test
    public void saveTicketTest() throws SQLException, ClassNotFoundException {
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
        Ticket ticket = new Ticket();

        ticket.setInTime(LocalDateTime.now().minusHours(1));
        ticket.setParkingSpot(parkingSpot);
        ticket.setVehicleRegNumber("ABCDEF");

        when(dataBaseTestConfig.getConnection()).thenReturn(con);
        when(con.prepareStatement(any())).thenReturn(ps);
        when(ps.execute()).thenReturn(true);
        assertTrue(ticketDAO.saveTicket(ticket));
    }

    @Test
    public void getTicketTest() {
        Ticket ticket = ticketDAO.getTicket("ABCDEF");
        assertNull(ticket);
    }

    @Test
    public void getTicketTest2() throws SQLException, ClassNotFoundException {

        when(dataBaseTestConfig.getConnection()).thenReturn(con);
        when(con.prepareStatement(any())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);

        Ticket ticket = ticketDAO.getTicket("TOTO");

        assertNotNull(ticket);
    }

    @Test
    public void upDateTest() throws SQLException, ClassNotFoundException {
        //GIVEN
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
        Ticket ticket = new Ticket(1,
                parkingSpot,
                "TOTO",
                0,
                LocalDateTime.now(),
                LocalDateTime.now());
        when(dataBaseTestConfig.getConnection()).thenReturn(con);
        when(con.prepareStatement(any())).thenReturn(ps);
        //WHEN
        boolean value = ticketDAO.updateTicket(ticket);
        //THEN
        assertTrue(value);


    }

    @Test
    public void upDateTicketTestShouldReturnFalse() throws SQLException, ClassNotFoundException {
        //GIVEN
        Ticket ticket = new Ticket();
        when(dataBaseTestConfig.getConnection()).thenReturn(con);
        when(con.prepareStatement(any())).thenReturn(ps);
        //WHEN
        boolean value = ticketDAO.updateTicket(ticket);
        //THEN
        assertFalse(value);
    }

    @Test
    public void isCarInsideTest() throws SQLException, ClassNotFoundException {
        //GIVEN
        Ticket ticket = new Ticket();
        when(dataBaseTestConfig.getConnection()).thenReturn(con);
        when(con.prepareStatement(any())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        //WHEN
        boolean value = ticketDAO.isCarInside(ticket.getVehicleRegNumber());
        //THEN
        assertTrue(value);
    }

    @Test
    public void isCarInsideTestShouldReturnFalse() throws SQLException, ClassNotFoundException {
        //GIVEN
        Ticket ticket = new Ticket();
        when(dataBaseTestConfig.getConnection()).thenReturn(con);
        when(con.prepareStatement(any())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);
        //WHEN
        boolean value = ticketDAO.isCarInside(ticket.getVehicleRegNumber());
        //THEN
        assertFalse(value);
    }

    @Test
    public void isCarClientTest() throws SQLException, ClassNotFoundException {
        //GIVEN
        Ticket ticket = new Ticket();
        when(dataBaseTestConfig.getConnection()).thenReturn(con);
        when(con.prepareStatement(any())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        //WHEN
        boolean value = ticketDAO.isCarClient(ticket.getVehicleRegNumber());
        //THEN
        assertTrue(value);
    }



    // TODO: Try to raise an error to validate catch case...

    @Test
    public void isCarClientTestShouldReturnFalse() throws SQLException, ClassNotFoundException {
        //GIVEN
        Ticket ticket = new Ticket();
        when(dataBaseTestConfig.getConnection()).thenReturn(con);
        when(con.prepareStatement(any())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);
        //WHEN
        boolean value = ticketDAO.isCarClient(ticket.getVehicleRegNumber());
        //THEN
        assertFalse(value);
    }

    @Test
    public void getTicketTestt() throws SQLException, ClassNotFoundException {
        LocalDateTime timeinExpected = LocalDateTime.now().minusDays(1);
        LocalDateTime timeOutExpect = LocalDateTime.now();
        //GIVEN
        when(dataBaseTestConfig.getConnection()).thenReturn(con);
        when(con.prepareStatement(any())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getTimestamp(5)).thenReturn(Timestamp.valueOf(timeinExpected));
        when(rs.getTimestamp(4)).thenReturn(Timestamp.valueOf(timeOutExpect));
        when(rs.getInt(1)).thenReturn(0);
        when(rs.getString(6)).thenReturn("CAR");

        //WHEN
        Ticket ticket = ticketDAO.getTicket("AZERTY");

        //THEN
        assertNotNull(ticket);
    }

    /*@Test
   public void getTicketTest3(){
        Ticket ticket = new Ticket();
    ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
        Ticket ticketExpected = new Ticket(1,
                parkingSpot,
                "TOTO",
                0,
                LocalDateTime.now(),
                LocalDateTime.now());

        verify();
        }*/
}





