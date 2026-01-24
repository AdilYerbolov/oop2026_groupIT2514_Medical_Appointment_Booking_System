package repositories;

import edu.aitu.oop3.db.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
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
 email varchar(100) not null,
 phone varchar(100) not null,
 available_from time not null,
 available_To time not null,
 password varchar(100) not null
 );
 """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.execute();
            System.out.println("Table doctors is ready.");
        }
    }
    public static void insertUser(String name, String specialization, String email, String phone, LocalTime avFrom, LocalTime avTo, String password) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "insert into doctors (name, specialization, email, phone, available_from, available_to, password) values (?, ?, ?, ?, ?, ?, ?) ";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, name);
                stmt.setString(2, specialization);
                stmt.setString(3, email);
                stmt.setString(4, phone);
                stmt.setObject(5, Time.valueOf(avFrom));
                stmt.setObject(6, Time.valueOf(avTo));
                stmt.setString(7, password);
                int rows = stmt.executeUpdate();
                System.out.println("Inserted rows: " + rows);
            }
        } catch(SQLException e){
            System.out.println("Database error: ");
            e.printStackTrace();
        }
    }
    public static String getUserPasswordByEmail(String email) throws SQLException{
        String sql = "select password from doctors where email = ?";
        ResultSet rs = null;
        try (Connection connection = DatabaseConnection.getConnection()) {
            try (PreparedStatement stmt = connection.prepareStatement(sql)){
                stmt.setString(1, email);
                rs = stmt.executeQuery();
                if(rs.next()){
                    return rs.getString("password");
                }
                else{
                    System.out.println("User not found");
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error:");
            e.printStackTrace();
        }
        return null;
    }
    public static int getId(String email) throws SQLException{
        String sql = "select id from doctors where email = ?";
        ResultSet rs = null;
        try (Connection connection = DatabaseConnection.getConnection()){
            try (PreparedStatement stmt = connection.prepareStatement(sql)){
                stmt.setString(1, email);
                rs = stmt.executeQuery();
                if (rs.next()){
                    return rs.getInt("id");
                }
                else{
                    System.out.println("User not found");
                    return -1;
                }
            }
        } catch (SQLException e){
            System.out.println("Database error:");
            e.printStackTrace();
        }
        return -1;
    }
    public static void doctorWorkingHours(int doctorId) throws SQLException{
        try (Connection connection = DatabaseConnection.getConnection()){
            String sql = "select name, available_from, available_to from doctors where id = ?";
            ResultSet rs = null;
            try (PreparedStatement stmt = connection.prepareStatement(sql)){
                stmt.setInt(1, doctorId);
                rs = stmt.executeQuery();
                if (rs.next()){
                    System.out.println("Doctor: " + rs.getString("name") + " works from " + rs.getTime("available_from") + " to " + rs.getTime("available_to"));
                }
                else{
                    System.out.println("There is no doctor with such ID!");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void printAllDoctors() throws SQLException{
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "select * from doctors order by id";
            ResultSet rs = null;
            try (PreparedStatement stmt = connection.prepareStatement(sql)){
                rs = stmt.executeQuery();
                System.out.println("Doctors: ");
                while (rs.next()){
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String specialization = rs.getString("specialization");
                    String email = rs.getString("email");
                    String phone = rs.getString("phone");
                    LocalTime avFrom = rs.getTime("available_from").toLocalTime();
                    LocalTime avTo = rs.getTime("available_to").toLocalTime();
                    System.out.println("ID: " + id + ", name: " + name + ", specialization: " + specialization + ", email: " + email + ", phone: " + phone + "available from: " + avFrom + " to " + avTo + "\n");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void changeSchedule(int doctorId, LocalTime avFrom, LocalTime avTo) throws SQLException{
        try (Connection connection = DatabaseConnection.getConnection()){
            String sql = "update doctors set available_from = ?, available_to = ?, where id = ?";
            int affectedRows = 0;
            try (PreparedStatement stmt = connection.prepareStatement(sql)){
                stmt.setTime(1, Time.valueOf(avFrom));
                stmt.setTime(2, Time.valueOf(avTo));
                stmt.setInt(3, doctorId);
                affectedRows = stmt.executeUpdate();
                if (affectedRows > 0){
                    System.out.println("Schedule updated successfully");
                }
                else {
                    System.out.println("failed to update the schedule, try again.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean checkAvailability(int doctorId, LocalTime timeSlot) throws SQLException{
        try (Connection connection = DatabaseConnection.getConnection()){
            String sql = "select available_from, available_to from doctors where id = ?";
            ResultSet rs = null;
            try (PreparedStatement stmt = connection.prepareStatement(sql)){
                stmt.setInt(1, doctorId);
                rs = stmt.executeQuery();
                if (rs.next()){
                    if (timeSlot.isAfter(rs.getTime("available_from").toLocalTime())){
                        if (timeSlot.isBefore(rs.getTime("available_to").toLocalTime())){
                            return true;
                        }
                        else {
                            return false;
                        }
                    }
                    else{
                        return false;
                    }
                } else{
                    throw new IllegalArgumentException("There is no doctor with such id!");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
