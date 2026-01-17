package entity;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {
    private int patientId;
    private int doctorId;
    private LocalDate date;
    private LocalTime time;
    private String status;

    public Appointment(int patientId, int doctorId,
                       LocalDate date, LocalTime time,
                       String status) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public int getPatientId() { return patientId; }
    public int getDoctorId() { return doctorId; }
    public LocalDate getDate() { return date; }
    public LocalTime getTime() { return time; }
    public String getStatus() { return status; }
}
