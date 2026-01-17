import edu.aitu.oop3.db.DatabaseConnection;
import exception.DoctorUnavailableException;
import exception.TimeSlotAlreadyBookedException;
import service.AppointmentService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        System.out.println("Connecting to Supabase...");
        try (Connection connection = DatabaseConnection.getConnection()) {
            System.out.println("Connected successfully!");
            String sql = "SELECT CURRENT_TIMESTAMP";
            try (PreparedStatement stmt = connection.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Database time: " + rs.getTimestamp(1));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while connecting to database:");
            e.printStackTrace();
        }

        AppointmentService service = new AppointmentService();

        int patientId = 1; // must exist in DB
        int doctorId = 1;  // must exist in DB

        try {
            // USER STORY 1: Book an appointment
            /* service.bookAppointment(
                    patientId,
                    doctorId,
                    LocalDate.of(2026, 1, 20),
                    LocalTime.of(13, 0)
            );
            System.out.println("Appointment booked successfully."); */

            // USER STORY 3: View doctor's schedule
            System.out.println("\nDoctor Schedule:");
            service.getDoctorSchedule(doctorId)
                    .forEach(a ->
                            System.out.println(
                                    a.getDate() + " " + a.getTime()
                            )
                    );

            // USER STORY 4: View patient's appointments
            /* System.out.println("\nPatient Appointments:");
            service.getPatientAppointments(patientId)
                    .forEach(a ->
                            System.out.println(
                                    a.getDate() + " " + a.getTime()
                            )
                    );

            // USER STORY 2: Cancel appointment (example ID = 1)
            service.cancelAppointment(1);
            System.out.println("\nAppointment cancelled."); */

        } catch (DoctorUnavailableException e) {
            System.out.println("Doctor unavailable.");
        } catch (TimeSlotAlreadyBookedException e) {
            System.out.println("Time slot already booked.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
