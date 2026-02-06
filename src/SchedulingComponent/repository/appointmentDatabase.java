package SchedulingComponent.repository;
import Interfaces.Repository;
import edu.aitu.oop3.db.DatabaseConnection;
import SchedulingComponent.exception.AppointmentNotFoundException;
import PatientRecordsComponent.exception.DoctorUnavailableException;
import SchedulingComponent.model.Appointment;
import SchedulingComponent.model.AppointmentSummary;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class appointmentDatabase implements Repository<Appointment> {
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
 create table if not exists appointments (
 id serial primary key,
 patient_id int not null,
 doctor_id int not null,
 appointment_date date not null,
 time_slot time not null,
 status varchar(100) not null,
 summary varchar(1000),
 appointment_type varchar(100) not null
 )
 """;//
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.execute();
            System.out.println("Table appointments is ready.");
        }
    }
    public boolean isTimeFree(int doctorId, LocalDate appointmentDate, LocalTime timeSlot){
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
    @Override
    public void insertUser(Appointment app) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "insert into appointments (patient_id, doctor_id, appointment_date, time_slot, status, appointment_type) values (?, ?, ?, ?, ?, ?) ";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, app.getPatientId());
                stmt.setInt(2, app.getDoctorId());
                stmt.setDate(3, Date.valueOf(app.getDate()));
                stmt.setTime(4, Time.valueOf(app.getTime()));
                stmt.setString(5, "Booked");
                stmt.setString(6, app.getType());
                int rows = stmt.executeUpdate();
                System.out.println("Inserted rows: " + rows);
            }
        } catch (SQLException e){
            System.out.println("Database error:");
            e.printStackTrace();
        }
    }
    public boolean isAppointmentCancelledSuccessfully(int appointmentId, int patientId){
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
    public void viewDoctorSchedule(int doctorId, LocalDate viewingDate) throws SQLException{
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
    public void viewUpcomingAppointmentsP(int patientId, LocalDate today, LocalTime now) throws SQLException{
        try (Connection connection = DatabaseConnection.getConnection()){
            String sql = "select * from appointments where patient_id = ? and appointment_date >= ? and status = 'Booked'";
            ResultSet rs = null;
            try (PreparedStatement stmt = connection.prepareStatement(sql)){
                stmt.setInt(1, patientId);
                stmt.setDate(2, Date.valueOf(today));
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
    public void viewUpcomingAppointmentsD(int doctorId, LocalDate today, LocalTime now){
        try (Connection connection = DatabaseConnection.getConnection()){
            String sql = "select * from appointments where doctor_id = ? and appointment_date >= ? and status = 'Booked'";
            ResultSet rs = null;
            try (PreparedStatement stmt = connection.prepareStatement(sql)){
                stmt.setInt(1, doctorId);
                stmt.setDate(2, Date.valueOf(today));
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
    public void viewMedicalRecordP(int patientId, LocalDate today, LocalTime now) throws SQLException{
        try (Connection connection = DatabaseConnection.getConnection()){
            String sql = "select * from appointments where patient_id = ? and appointment_date <= ? and status = 'Booked'";
            ResultSet rs = null;
            try (PreparedStatement stmt = connection.prepareStatement(sql)){
                stmt.setInt(1, patientId);
                stmt.setDate(2, Date.valueOf(today));
                rs = stmt.executeQuery();
                if(rs.next()){
                    System.out.println("Your appointments: \n");
                    System.out.println("id: " + rs.getInt("id") + " " + rs.getDate("appointment_date") + " at " + rs.getTime("time_slot") + "\nSummary: " + rs.getString("summary"));
                    while (rs.next()){
                        System.out.println("id: " + rs.getInt("id") + " " + rs.getDate("appointment_date") + " at " + rs.getTime("time_slot") + "\nSummary: " + rs.getString("summary"));
                    }
                }
                else {
                    System.out.println("Your medical record is empty");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void viewMedicalRecordD(int doctorId, LocalDate today, LocalTime now) throws SQLException{
        try (Connection connection = DatabaseConnection.getConnection()){
            String sql = "select * from appointments where doctor_id = ? and appointment_date <= ? and status = 'Booked'";
            ResultSet rs = null;
            try (PreparedStatement stmt = connection.prepareStatement(sql)){
                stmt.setInt(1, doctorId);
                stmt.setDate(2, Date.valueOf(today));
                rs = stmt.executeQuery();
                if(rs.next()){
                    System.out.println("Your appointments: \n");
                    System.out.println("id: " + rs.getInt("id") + " " + rs.getDate("appointment_date") + " at " + rs.getTime("time_slot") + "\nSummary: " + rs.getString("summary"));
                    while (rs.next()){
                        System.out.println("id: " + rs.getInt("id") + " " + rs.getDate("appointment_date") + " at " + rs.getTime("time_slot") + "\nSummary: " + rs.getString("summary"));
                    }
                }
                else {
                    System.out.println("Your medical record is empty");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Appointment getAppointment(int appId, int doctorId, LocalDate today) throws SQLException{
        try (Connection connection = DatabaseConnection.getConnection()){
            String sql = "select * from appointments where id = ? and doctor_id = ? and appointment_date < ? and status = 'Booked'";
            ResultSet rs = null;
            try (PreparedStatement stmt = connection.prepareStatement(sql)){
                stmt.setInt(1, appId);
                stmt.setInt(2, doctorId);
                stmt.setDate(3, Date.valueOf(today));
                rs = stmt.executeQuery();
                if (rs.next()){
                    return new Appointment(rs.getInt("patient_id"), rs.getInt("doctor_id"), LocalDate.parse(rs.getString("appointment_date")), LocalTime.parse(rs.getString("time_slot")));
                }
                else{
                    throw new AppointmentNotFoundException("Appointment not found!");
                }
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
    public void addSummary(AppointmentSummary appS, int appId)throws SQLException{
        try (Connection connection = DatabaseConnection.getConnection()){
            String sql = "update appointments set summary = ? where id = ?";
            int affectedRows = 0;
            try (PreparedStatement stmt = connection.prepareStatement(sql)){
                stmt.setString(1, appS.getSummary());
                stmt.setInt(2, appId);
                affectedRows = stmt.executeUpdate();
                if (affectedRows > 0){
                    System.out.println("Summary added successfully!");
                }
                else{
                    System.out.println("Failed to add summary. Please try again");
                }
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
