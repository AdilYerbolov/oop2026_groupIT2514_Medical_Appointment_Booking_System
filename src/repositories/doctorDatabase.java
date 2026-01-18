package repositories;

import edu.aitu.oop3.db.DatabaseConnection;

import java.sql.*;
import java.time.LocalTime;

public class doctorDatabase {
    public static void main(String[] args) {
        System.out.println("connecting...");
        try (Connection connection = DatabaseConnection.getConnection()) {
            createTableIfNeeded(connection);
        } catch (SQLException e) {
            System.out.println("Database error:");
            e.printStackTrace();
        }
    }
    private static void createTableIfNeeded(Connection connection) throws SQLException {
        String sql = """
 create table if not exists doctors (
 id serial primary key,
 name varchar(100) not null,
 specialization varchar(100) not null,
 available_from time not null,
 available_To time not null
 );
 """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.execute();
            System.out.println("Table doctors is ready.");
        }
    }
    private static void insertUser(Connection connection, String name, String specialization, LocalTime avFrom, LocalTime avTo) throws SQLException {
        String sql = "insert into doctors (name, specialization, available_from, available_to) values (?, ?, ?, ?) ";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, specialization);
            stmt.setObject(3, Time.valueOf(avFrom));
            stmt.setObject(4, Time.valueOf(avTo));
            int rows = stmt.executeUpdate();
            System.out.println("Inserted rows: " + rows);
        }
    }
}
