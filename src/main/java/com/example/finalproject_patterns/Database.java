package com.example.finalproject_patterns;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {

    private static final String URL = "jdbc:sqlite:conference.db";

    private static final Logger LOGGER =
            Logger.getLogger(Database.class.getName());

    public static Connection getConnection() throws SQLException {

        try {
            Connection connection = DriverManager.getConnection(URL);

            connection.createStatement()
                    .execute("PRAGMA foreign_keys = ON;");

            return connection;

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,
                    "Could not connect to the database", e);

            throw e;
        }
    }
}