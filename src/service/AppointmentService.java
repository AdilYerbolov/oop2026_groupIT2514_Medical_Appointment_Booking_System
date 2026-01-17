package service;

import entity.*;
import exception.*;
import repository.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AppointmentService {

    private final DoctorRepository doctorRepo = new DoctorRepository();
    private final AppointmentRepository appointmentRepo =
            new AppointmentRepository();

    // USER STORY 1: Book an appointment
    public void bookAppointment(int patientId,
                                int doctorId,
                                LocalDate date,
                                LocalTime time) {

        Doctor doctor = doctorRepo.findById(doctorId);

        if (doctor == null ||
                time.isBefore(doctor.getAvailableFrom()) ||
                time.isAfter(doctor.getAvailableTo())) {
            throw new DoctorUnavailableException();
        }

        if (appointmentRepo.exists(doctorId, date, time)) {
            throw new TimeSlotAlreadyBookedException();
        }

        appointmentRepo.save(
                new Appointment(
                        patientId,
                        doctorId,
                        date,
                        time,
                        "BOOKED"
                )
        );
    }

    // USER STORY 2: Cancel an appointment
    public void cancelAppointment(int appointmentId) {
        appointmentRepo.updateStatus(appointmentId, "CANCELLED");
    }

    // USER STORY 3: View a doctor's schedule
    public List<Appointment> getDoctorSchedule(int doctorId) {
        return appointmentRepo.findByDoctor(doctorId);
    }

    // USER STORY 4: View patient's upcoming appointments
    public List<Appointment> getPatientAppointments(int patientId) {
        return appointmentRepo.findByPatient(patientId);
    }
}
