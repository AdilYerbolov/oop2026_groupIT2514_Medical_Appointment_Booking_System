package models;

import java.time.LocalDate;
import java.time.LocalTime;

public class InPersonAppointment extends Appointment{
    public InPersonAppointment(int patientId, int doctorId, LocalDate date, LocalTime time){
        super(patientId, doctorId, date, time);
        super.setType("in person");
    }
}
