package repositories;
import edu.aitu.oop3.db.DatabaseConnection;

import java.sql.*;

public class patientsDatabase {
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
 create table if not exists patients (
 id serial primary key,
 name varchar(100) not null,
 email varchar(100) not null,
phone varchar(100) not null
 );
 """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.execute();
            System.out.println("Table patients is ready.");
        }
    }
    private static void insertUser(Connection connection, String name, String email, String phone) throws SQLException {
        String sql = "insert into patients (name, email, phone) values (?, ?, ?) ";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phone);
            int rows = stmt.executeUpdate();
            System.out.println("Inserted rows: " + rows);
        }
    }
}
