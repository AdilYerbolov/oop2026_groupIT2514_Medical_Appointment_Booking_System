package SchedulingComponent.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {
    private int id;
    private int patientId;
    private int doctorId;
    private LocalDate date;
    private LocalTime time;
    private String type;

    public Appointment(int patientId, int doctorId, LocalDate date, LocalTime time){
        setPatientId(patientId);
        setDoctorId(doctorId);
        setDate(date);
        setTime(time);
    }

    public int getId(){
        return id;
    }
    public int getPatientId(){
        return patientId;
    }
    public int getDoctorId() {
        return doctorId;
    }
    public LocalDate getDate(){
        return date;
    }
    public LocalTime getTime() {
        return time;
    }
    public String getType(){
        return type;
    }
    public void setId(int id){
        this.id = id;
    }
    public void setPatientId(int patientId){
        this.patientId = patientId;
    }
    public void setDoctorId(int doctorId){
        this.doctorId = doctorId;
    }
    public void setDate(LocalDate date){
        this.date = date;
    }
    public void setTime(LocalTime time){
        this.time = time;
    }
    public void setType(String type){
        this.type = type;
    }
}
