package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.model.ParkingSpot;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(MockitoExtension.class)
public class ParkingSpotDAOTest {
    private static DataBaseTestConfig dataBaseTestConfig=new DataBaseTestConfig();
    private static ParkingSpotDAO parkingSpotDAO;



    @BeforeAll
    private static void setUp(){
        parkingSpotDAO=new ParkingSpotDAO();
        parkingSpotDAO.dataBaseConfig=dataBaseTestConfig;
    }

    @Test
    public void getNextAvailableSlotTest(){
        int resolve=parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR);
        assertNotEquals(0,resolve);
    }
}

