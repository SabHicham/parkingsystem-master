package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InteractiveShellTest {
    //Class to be tested
    private static InteractiveShell interactiveShell;

    @Mock
    private static InputReaderUtil inputReaderUtil;
    @Mock
    private static ParkingSpotDAO parkingSpotDAO;
    @Mock
    private static TicketDAO ticketDAO;
    @Mock
    private static ParkingService parkingService;

    @BeforeEach
    private void setUp(){
        interactiveShell = new InteractiveShell(inputReaderUtil, parkingSpotDAO, ticketDAO, parkingService);
    }

    @Test
    void loadInterface() {
        //GIVEN
        when(inputReaderUtil.readSelection()).thenReturn(1).thenReturn(3);
        //WHEN
        interactiveShell.loadInterface();
        //THEN
        verify(parkingService, Mockito.times(1)).processIncomingVehicle();
    }

    @Test
    void loadInterface2() {
        //GIVEN
        when(inputReaderUtil.readSelection()).thenReturn(2).thenReturn(3);
        //WHEN
        interactiveShell.loadInterface();
        //THEN
        verify(parkingService, Mockito.times(1)).processExitingVehicle();
    }

    @Test
    void loadInterface3() {
        //GIVEN
        when(inputReaderUtil.readSelection()).thenReturn(2).thenReturn(2).thenReturn(3);
        //WHEN
        interactiveShell.loadInterface();
        //THEN
        verify(parkingService, Mockito.times(2)).processExitingVehicle();
    }
    @Test
    void loadInterface4() {
        //GIVEN

        //WHEN
        interactiveShell = new InteractiveShell(inputReaderUtil, parkingSpotDAO, ticketDAO, null);

        //THEN
        assertNotNull(interactiveShell.getParkingService());
    }
}