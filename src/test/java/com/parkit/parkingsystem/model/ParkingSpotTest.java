package com.parkit.parkingsystem.model;


import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.sun.org.apache.xpath.internal.objects.XNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import sun.security.krb5.internal.PAData;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(MockitoExtension.class)
public class ParkingSpotTest {


    @BeforeEach
    private void setUpPerTest() {

    }

    @Test
    public void equalTest() {
        ParkingSpot parkingSpot = new ParkingSpot(0, ParkingType.CAR, true);
        ParkingSpot parkingSpot1 = new ParkingSpot(0, ParkingType.CAR, true);

        assertTrue(parkingSpot.equals(parkingSpot1));


    }
}