package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.Ticket;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class TicketDAOTest {
    private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static DataBasePrepareService dataBasePrepareService;
    private static TicketDAO ticketDAO;

    @BeforeAll
    private static void setUp(){
        ticketDAO= new TicketDAO();
        ticketDAO.dataBaseConfig=dataBaseTestConfig;
        dataBasePrepareService= new DataBasePrepareService();
        dataBasePrepareService.clearDataBaseEntries();
    }
    @Test
    public void getTicketTest(){
        Ticket ticket= ticketDAO.getTicket("ABCDEF");
        assertNull(ticket);

    }

}
