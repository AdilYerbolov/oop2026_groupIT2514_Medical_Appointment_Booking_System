package services;

import exceptions.AppointmentNotFoundException;
import exceptions.DoctorUnavailableException;
import models.*;
import repositories.doctorDatabase;
import repositories.patientsDatabase;
import repositories.appointmentDatabase;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class AppService {
    doctorDatabase doctorDatabase = new doctorDatabase();
    patientsDatabase patientsDatabase = new patientsDatabase();
    appointmentDatabase appointmentDatabase = new appointmentDatabase();

    private static final AppService INSTANCE = new AppService();

    private AppService(){}


    public static AppService getInstance(){
        return INSTANCE;
    }
    public boolean isUserVerified(String email, String password, boolean isDoctor){
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
    public boolean isPatientCreated (Patient user){
        try {
            patientsDatabase.insertUser(user);
            return true;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    public boolean isDoctorCreated(Doctor user){
        try {
            doctorDatabase.insertUser(user);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public int getUserId(String email, boolean isDoctor){
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
    public boolean bookAppointment(Appointment app){
        try {
            if (doctorDatabase.checkAvailability(app.getDoctorId(), app.getTime())) {
                if (appointmentDatabase.isTimeFree(app.getDoctorId(), app.getDate(), app.getTime())) {
                    appointmentDatabase.insertUser(app);
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
    public boolean isAppointmentCancelledSuccessfully(int patientId, int appointmentId){
        if(appointmentDatabase.isAppointmentCancelledSuccessfully(appointmentId, patientId)){
            return true;
        }
        else{
            throw new AppointmentNotFoundException("Appointment not found!");
        }
    }
    public void viewDoctorsSchedule(int doctorId, LocalDate viewingDate){
        try {
            doctorDatabase.doctorWorkingHours(doctorId);
            appointmentDatabase.viewDoctorSchedule(doctorId, viewingDate);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void viewUpcomingAppointments(int id, boolean isDoctor){
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
    public void viewDoctorsList(){
        try {
            doctorDatabase.printAllDoctors();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateSchedule(int doctorId, LocalTime avFrom, LocalTime avTo){
        try{
            doctorDatabase.changeSchedule(doctorId, avFrom, avTo);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void viewMedicalRecord(int id, boolean isDoctor){
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        try {
            if (!isDoctor) {
                appointmentDatabase.viewMedicalRecordP(id, today, now);
            }
            else{
                appointmentDatabase.viewMedicalRecordD(id, today, now);
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public void addSummary(int appId, int doctorId, Scanner sc){
        LocalDate today = LocalDate.now();
        Appointment app;
        try {
            app = appointmentDatabase.getAppointment(appId, doctorId, today);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Appointment found successfully!\nSummary: ");
        String summary = sc.nextLine();
        AppointmentSummary appSummary = AppointmentSummary.builder(appId, app.getPatientId(), doctorId)
                .addSummary(summary)
                .build();
        try {
            appointmentDatabase.addSummary(appSummary, appId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
