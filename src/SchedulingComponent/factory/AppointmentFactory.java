package SchedulingComponent.factory;

import SchedulingComponent.model.Appointment;
import SchedulingComponent.model.FollowUpAppointment;
import SchedulingComponent.model.InPersonAppointment;
import SchedulingComponent.model.OnlineAppointment;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentFactory {
    public static Appointment getAppointment(int type, int patientId, int doctorId, LocalDate date, LocalTime time){
        switch(type){
            case 1: return new InPersonAppointment(patientId, doctorId, date, time);
            case 2: return  new OnlineAppointment(patientId, doctorId, date, time);
            case 3: return  new FollowUpAppointment(patientId, doctorId, date, time);
            default: return null;
        }
    }
}
