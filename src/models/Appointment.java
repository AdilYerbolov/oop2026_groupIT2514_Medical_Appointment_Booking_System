package models;

import java.time.LocalDate;
import java.time.LocalTime;

public abstract class Appointment {
    protected int id;
    protected int patientId;
    protected int doctorId;
    protected LocalDate date;
    protected LocalTime time;
    protected AppointmentStatus status;

    protected Appointment(int patientId, int doctorId, LocalDate date, LocalTime time) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.time = time;
        this.status = AppointmentStatus.BOOKED;
    }

    public abstract String getType();

    public int getPatientId() { return patientId; }
    public int getDoctorId() { return doctorId; }
    public LocalDate getDate() { return date; }
    public LocalTime getTime() { return time; }
    public AppointmentStatus getStatus() { return status; }
}
