package repositories;
import edu.aitu.oop3.db.DatabaseConnection;

import java.sql.*;
import java.time.LocalTime;

public class appointmentDatabase {
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
 create table if not exists appointments (
 id serial primary key,
 patient_id int not null,
 doctor_id int not null,
 appointment_date date not null,
 time_slot time not null,
 status varchar(100) not null
 )
 """;//
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.execute();
            System.out.println("Table appointments is ready.");
        }
    }
    private static void insertUser(Connection connection, int patientId, int doctorId, Date appointmentDate, LocalTime timeSlot, String status) throws SQLException {
        String sql = "insert into appointments (patient_id, doctor_id, appointment_date, time_slot, status) values (?, ?, ?, ?, ?) ";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, patientId);
            stmt.setInt(2, doctorId);
            stmt.setDate(3, appointmentDate);
            stmt.setTime(4, Time.valueOf(timeSlot));
            stmt.setString(5, status);
            int rows = stmt.executeUpdate();
            System.out.println("Inserted rows: " + rows);
        }
    }
}
