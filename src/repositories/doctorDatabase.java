package repositories;

import Interfaces.Repository;
import edu.aitu.oop3.db.DatabaseConnection;
import models.Doctor;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class doctorDatabase implements Repository<Doctor> {
    public void main(String[] args) {
        System.out.println("connecting...");
        try (Connection connection = DatabaseConnection.getConnection()) {
            createTableIfNeeded(connection);
        } catch (SQLException e) {
            System.out.println("Database error:");
            e.printStackTrace();
        }
    }
    private void createTableIfNeeded(Connection connection) throws SQLException {
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

    @Override
    public void insertUser(Doctor user) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "insert into doctors (name, specialization, email, phone, available_from, available_to, password) values (?, ?, ?, ?, ?, ?, ?) ";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, user.getName());
                stmt.setString(2, user.getSpecialization());
                stmt.setString(3, user.getEmail());
                stmt.setString(4, user.getPhone());
                stmt.setObject(5, Time.valueOf(user.getAvFrom()));
                stmt.setObject(6, Time.valueOf(user.getAvTo()));
                stmt.setString(7, user.getPassword());
                int rows = stmt.executeUpdate();
                System.out.println("Inserted rows: " + rows);
            }
        } catch(SQLException e){
            System.out.println("Database error: ");
            e.printStackTrace();
        }
    }

    public String getUserPasswordByEmail(String email) throws SQLException{
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
    public int getId(String email) throws SQLException{
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
    public void doctorWorkingHours(int doctorId) throws SQLException{
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
    public void printAllDoctors() throws SQLException{
        List<Doctor> arr = new ArrayList<>();
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
                    arr.add(new Doctor(name, email, phone, specialization, avFrom, avTo, "somepass"));
                }
                arr.forEach(doctor -> System.out.println(doctor));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void changeSchedule(int doctorId, LocalTime avFrom, LocalTime avTo) throws SQLException{
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
    public boolean checkAvailability(int doctorId, LocalTime timeSlot) throws SQLException{
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
