package repository;

import edu.aitu.oop3.db.DatabaseConnection;
import entity.Appointment;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentRepository {

    // Check if a doctor already has an appointment at given date & time
    public boolean exists(int doctorId, LocalDate date, LocalTime time) {
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "SELECT 1 FROM appointments " +
                            "WHERE doctor_id=? AND appointment_date=? AND time_slot=? " +
                            "AND status='BOOKED'"
            );
            ps.setInt(1, doctorId);
            ps.setDate(2, Date.valueOf(date));
            ps.setTime(3, Time.valueOf(time));

            return ps.executeQuery().next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Save new appointment
    public void save(Appointment a) {
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO appointments " +
                            "(patient_id, doctor_id, appointment_date, time_slot, status) " +
                            "VALUES (?, ?, ?, ?, ?)"
            );
            ps.setInt(1, a.getPatientId());
            ps.setInt(2, a.getDoctorId());
            ps.setDate(3, Date.valueOf(a.getDate()));
            ps.setTime(4, Time.valueOf(a.getTime()));
            ps.setString(5, a.getStatus());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // USER STORY 2: Cancel appointment
    public void updateStatus(int appointmentId, String status) {
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE appointments SET status=? WHERE id=?"
            );
            ps.setString(1, status);
            ps.setInt(2, appointmentId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // USER STORY 3: View doctor's schedule
    public List<Appointment> findByDoctor(int doctorId) {
        List<Appointment> list = new ArrayList<>();
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM appointments " +
                            "WHERE doctor_id=? AND status='BOOKED'"
            );
            ps.setInt(1, doctorId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Appointment(
                        rs.getInt("patient_id"),
                        rs.getInt("doctor_id"),
                        rs.getDate("appointment_date").toLocalDate(),
                        rs.getTime("time_slot").toLocalTime(),
                        rs.getString("status")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // USER STORY 4: View patient's appointments
    public List<Appointment> findByPatient(int patientId) {
        List<Appointment> list = new ArrayList<>();
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM appointments " +
                            "WHERE patient_id=? AND status='BOOKED'"
            );
            ps.setInt(1, patientId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Appointment(
                        rs.getInt("patient_id"),
                        rs.getInt("doctor_id"),
                        rs.getDate("appointment_date").toLocalDate(),
                        rs.getTime("time_slot").toLocalTime(),
                        rs.getString("status")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
