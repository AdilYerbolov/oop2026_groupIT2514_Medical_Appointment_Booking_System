package services;

import exceptions.AppointmentNotFoundException;
import exceptions.DoctorUnavailableException;
import repositories.doctorDatabase;
import repositories.patientsDatabase;
import repositories.appointmentDatabase;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class AppService {
    public static boolean isUserVerified(String email, String password, boolean isDoctor){
        String dbPassword;
        if (!isDoctor) {
            try {
                dbPassword = patientsDatabase.getUserPasswordByEmail(email);
            } catch (SQLException e){
                System.out.println("Database error: ");
                e.printStackTrace();
                return false;
            }
        }
        else{
            try {
                dbPassword = doctorDatabase.getUserPasswordByEmail(email);
            } catch (SQLException e){
                System.out.println("Database error: ");
                e.printStackTrace();
                return false;
            }
        }
        if (dbPassword.equals(password)){
            return true;
        }
        else{
            return false;
        }
    }
    public static boolean isPatientCreated(String name, String email, String phone, String password){
        try {
            patientsDatabase.insertUser(name, email, phone, password);
            return true;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    public static boolean isDoctorCreated(String name, String specialization, String email, String phone, LocalTime avFrom, LocalTime avTo, String password){
        try {
            doctorDatabase.insertUser(name, specialization, email, phone, avFrom, avTo, password);
            return true;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    public static int getUserId(String email, boolean isDoctor){
        if (!isDoctor){
            try {
                int id = patientsDatabase.getId(email);
                return id;
            } catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
        else {
            try {
                int id = doctorDatabase.getId(email);
                return id;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static boolean bookAppointment(int patientId, int doctorId, LocalDate appointmentDate, LocalTime timeSlot){
        try {
            if (doctorDatabase.checkAvailability(doctorId, timeSlot)) {
                if (appointmentDatabase.isTimeFree(doctorId, appointmentDate, timeSlot)) {
                    appointmentDatabase.insertUser(patientId, doctorId, appointmentDate, timeSlot, "Booked");
                    return true;
                }
                else{
                    throw new DoctorUnavailableException("Doctor is unavailable!");
                }
            }
            else {
                throw new DoctorUnavailableException("Doctor is unavailable!");
            }
        }
        catch (SQLException e){
            System.out.println("Database error: ");
            e.printStackTrace();
            return false;
        }
    }
    public static boolean isAppointmentCancelledSuccessfully(int patientId, int appointmentId){
        if(appointmentDatabase.isAppointmentCancelledSuccessfully(appointmentId, patientId)){
            return true;
        }
        else{
            return false;
        }
    }
    public static void viewDoctorsSchedule(int doctorId, LocalDate viewingDate){
        try {
            doctorDatabase.doctorWorkingHours(doctorId);
            appointmentDatabase.viewDoctorSchedule(doctorId, viewingDate);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void viewUpcomingAppointments(int id, boolean isDoctor){
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        try {
            if (!isDoctor) {
                appointmentDatabase.viewUpcomingAppointmentsP(id, today, now);
            }
            else {
                appointmentDatabase.viewUpcomingAppointmentsD(id, today, now);
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public static void viewDoctorsList(){
        try {
            doctorDatabase.printAllDoctors();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void updateSchedule(int doctorId, LocalTime avFrom, LocalTime avTo){
        try{
            doctorDatabase.changeSchedule(doctorId, avFrom, avTo);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
