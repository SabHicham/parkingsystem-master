package com.parkit.parkingsystem.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)


class DataBaseConfigTest {
    @Mock
    private static Connection connection;
    @Mock
    private static PreparedStatement preparedStatement;
    @Mock
    private static ResultSet resultSet;

    @Test
    void getConnection() throws SQLException, ClassNotFoundException {

        DataBaseConfig dataBaseConfig = new DataBaseConfig();
        Connection subjet = dataBaseConfig.getConnection();
        assertNotNull(subjet);
    }

    @Test
    void closeConnection() throws SQLException {

        DataBaseConfig dataBaseConfig = new DataBaseConfig();
        dataBaseConfig.closeConnection(connection);
        verify(connection, Mockito.times(1)).close();

    }

    @Test
    void closeConnectionNull() throws SQLException {

        DataBaseConfig dataBaseConfig = new DataBaseConfig();
        dataBaseConfig.closeConnection(null);
        verify(connection, Mockito.times(0)).close();
    }

    @Test
    void closePreparedStatement() throws SQLException {
        DataBaseConfig dataBaseConfig = new DataBaseConfig();
        dataBaseConfig.closePreparedStatement(preparedStatement);
        verify(preparedStatement, Mockito.times(1)).close();
    }

    @Test
    void closePreparedStatementNull() throws SQLException {
        DataBaseConfig dataBaseConfig = new DataBaseConfig();
        dataBaseConfig.closePreparedStatement(null);
        verify(preparedStatement, Mockito.times(0)).close();
    }

    @Test
    void closeResultSet() throws SQLException {
        DataBaseConfig dataBaseConfig = new DataBaseConfig();
        dataBaseConfig.closeResultSet(resultSet);
        verify(resultSet, Mockito.times(1)).close();
    }

    @Test
    void closeResultSetNull() throws SQLException {
        DataBaseConfig dataBaseConfig = new DataBaseConfig();
        dataBaseConfig.closeResultSet(null);
        verify(resultSet, Mockito.times(0)).close();
    }

}