package models;

import java.time.LocalDate;
import java.time.LocalTime;

public class FollowUpAppointment extends Appointment{
    public FollowUpAppointment(int patientId, int doctorId, LocalDate date, LocalTime time){
        super(patientId, doctorId, date, time);
        super.setType("follow up");
    }
}
