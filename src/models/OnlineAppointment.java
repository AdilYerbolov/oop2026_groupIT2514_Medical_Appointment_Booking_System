package models;

import java.time.LocalDate;
import java.time.LocalTime;

public class OnlineAppointment extends Appointment{
    public OnlineAppointment(int patientId, int doctorId, LocalDate date, LocalTime time){
        super(patientId, doctorId, date, time);
        super.setType("online");
    }
}
