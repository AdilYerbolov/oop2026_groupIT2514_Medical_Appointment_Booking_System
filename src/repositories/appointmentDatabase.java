package repositories;
import edu.aitu.oop3.db.DatabaseConnection;
import exceptions.AppointmentNotFoundException;
import exceptions.DoctorUnavailableException;

import javax.xml.crypto.Data;
import java.sql.*;
import java.time.LocalDate;
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
    public static boolean isTimeFree(int doctorId, LocalDate appointmentDate, LocalTime timeSlot){
        try (Connection connection = DatabaseConnection.getConnection()){
            String sql = "select status from appointments where doctor_id = ? and appointment_date = ? and time_slot = ? and status = 'booked'";
            ResultSet rs = null;
            try (PreparedStatement stmt = connection.prepareStatement(sql)){
                stmt.setInt(1, doctorId);
                stmt.setDate(2, Date.valueOf(appointmentDate));
                stmt.setTime(3, Time.valueOf(timeSlot));
                rs = stmt.executeQuery();
                if (!rs.next()){
                    return true;
                }
                else {
                    throw new DoctorUnavailableException("Doctor is unavailable at that date and time!");
                }
            }
        } catch (SQLException e){
            System.out.println("Database error: ");
            e.printStackTrace();
            return false;
        }
    }
    public static void insertUser(int patientId, int doctorId, LocalDate appointmentDate, LocalTime timeSlot, String status) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "insert into appointments (patient_id, doctor_id, appointment_date, time_slot, status) values (?, ?, ?, ?, ?) ";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, patientId);
                stmt.setInt(2, doctorId);
                stmt.setDate(3, Date.valueOf(appointmentDate));
                stmt.setTime(4, Time.valueOf(timeSlot));
                stmt.setString(5, status);
                int rows = stmt.executeUpdate();
                System.out.println("Inserted rows: " + rows);
            }
        } catch (SQLException e){
            System.out.println("Database error:");
            e.printStackTrace();
        }
    }
    public static boolean isAppointmentCancelledSuccessfully(int appointmentId, int patientId){
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "update appointments set status = 'Canceled' where id = ? and patient_id = ? and status = 'Booked'";
            int affectedRows = 0;
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, appointmentId);
                stmt.setInt(2, patientId);
                affectedRows = stmt.executeUpdate();
                if (affectedRows > 0){
                    return true;
                }
                else {
                    throw new AppointmentNotFoundException("There is no booked appointment!");
                }
            }
        } catch (SQLException e){
            System.out.println("Database error:");
            e.printStackTrace();
            return false;
        }
    }
    public static void viewDoctorSchedule(int doctorId, LocalDate viewingDate) throws SQLException{
        try (Connection connection = DatabaseConnection.getConnection()){
            String sql = "select time_slot from appointments where doctor_id = ? and appointment_date = ?";
            ResultSet rs = null;
            try (PreparedStatement stmt = connection.prepareStatement(sql)){
                stmt.setInt(1, doctorId);
                stmt.setDate(2, Date.valueOf(viewingDate));
                rs = stmt.executeQuery();
                if(rs.next()) {
                    System.out.println("Doctor has appointments on: ");
                    System.out.println(rs.getTime("time_slot") + "\n");
                    while (rs.next()) {
                        System.out.println(rs.getTime("time_slot") + "\n");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void viewUpcomingAppointmentsP(int patientId, LocalDate today, LocalTime now) throws SQLException{
        try (Connection connection = DatabaseConnection.getConnection()){
            String sql = "select * from appointments where patient_id = ? and appointment_date > ? and time_slot > ? and status = 'Booked'";
            ResultSet rs = null;
            try (PreparedStatement stmt = connection.prepareStatement(sql)){
                stmt.setInt(1, patientId);
                stmt.setDate(2, Date.valueOf(today));
                stmt.setTime(3, Time.valueOf(now));
                rs = stmt.executeQuery();
                if(rs.next()){
                    System.out.println("Upcoming appointments: \n");
                    System.out.println("id: " + rs.getInt("id") + " " + rs.getDate("appointment_date") + " at " + rs.getTime("time_slot"));
                    while (rs.next()){
                        System.out.println("id: " + rs.getInt("id") + " " + rs.getDate("appointment_date") + " at " + rs.getTime("time_slot"));
                    }
                }
                else {
                    System.out.println("You have no upcoming appointments");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void viewUpcomingAppointmentsD(int doctorId, LocalDate today, LocalTime now){
        try (Connection connection = DatabaseConnection.getConnection()){
            String sql = "select * from appointments where doctor_id = ? and appointment_date > ?";
            ResultSet rs = null;
            try (PreparedStatement stmt = connection.prepareStatement(sql)){
                stmt.setInt(1, doctorId);
                stmt.setDate(2, Date.valueOf(today));
                stmt.setTime(3, Time.valueOf(now));
                rs = stmt.executeQuery();
                if(rs.next()){
                    System.out.println("Upcoming appointments: \n");
                    System.out.println("id: " + rs.getInt("id") + " " + rs.getDate("appointment_date") + " at " + rs.getTime("time_slot"));
                    while (rs.next()){
                        System.out.println("id: " + rs.getInt("id") + " " + rs.getDate("appointment_date") + " at " + rs.getTime("time_slot"));
                    }
                }
                else {
                    System.out.println("You have no upcoming appointments");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }//
}
