package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.model.ParkingSpot;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ParkingSpotDAOTest {

    @Mock
    private static DataBaseConfig dataBaseConfig;

    //Class to be tested
    private static ParkingSpotDAO parkingSpotDAO;

    @Mock
    private static Connection con;

    @Mock
    private static PreparedStatement ps;

    @Mock
    private static ResultSet rs;

    @BeforeEach
    private void setUp(){
        parkingSpotDAO=new ParkingSpotDAO(dataBaseConfig);
    }

    @Test
    public void getNextAvailableSlotTest() throws SQLException, ClassNotFoundException {
        //GIVEN
        when(dataBaseConfig.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt(anyInt())).thenReturn(2);
        //WHEN
        int resolve=parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR);
        //THEN
        assertNotEquals(0,resolve);
    }
}

