package models;

import java.time.LocalTime;
import java.util.Date;

public class appointments {
    private int id;
    private int patientId;
    private int doctorId;
    private Date date;
    private LocalTime time;

    public appointments(int patientId, int doctorId, Date date, LocalTime time){
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
    public Date getDate(){
        return date;
    }
    public LocalTime getTime() {
        return time;
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
    public void setDate(Date date){
        this.date = date;
    }
    public void setTime(LocalTime time){
        this.time = time;
    }
}
