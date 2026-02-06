package SchedulingComponent.service;

import NotificationComponent.service.NotificationService;
import SchedulingComponent.model.Appointment;
import SchedulingComponent.model.AppointmentSummary;
import SchedulingComponent.exception.AppointmentNotFoundException;
import PatientRecordsComponent.exception.DoctorUnavailableException;
import PatientRecordsComponent.repository.doctorDatabase;
import PatientRecordsComponent.repository.patientsDatabase;
import SchedulingComponent.repository.appointmentDatabase;

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
        NotificationService.send("Appointment found successfully!");
        System.out.println("Summary: ");
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
