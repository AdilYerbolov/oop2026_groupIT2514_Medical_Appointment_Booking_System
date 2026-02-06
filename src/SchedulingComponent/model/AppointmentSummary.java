package SchedulingComponent.model;

public class AppointmentSummary {
    private int appointmentId;
    private int patientId;
    private int doctorId;
    private String summary;

    public AppointmentSummary(AppointmentSummaryBuilder builder){
        this.appointmentId = builder.appointmentId;
        this.patientId = builder.patientId;
        this.doctorId = builder.doctorId;
        this.summary = builder.summary;
    }

    public int getAppointmentId(){
        return appointmentId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public int getPatientId() {
        return patientId;
    }

    public String getSummary() {
        return summary;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }
    public static AppointmentSummaryBuilder builder(int appId, int patientId, int doctorId){
        return new AppointmentSummaryBuilder(appId, patientId, doctorId);
    }

    public static class AppointmentSummaryBuilder {
        private int appointmentId;
        private int patientId;
        private int doctorId;
        private String summary;

        public AppointmentSummaryBuilder(int appointmentId, int patientId, int doctorId){
            this.appointmentId = appointmentId;
            this.patientId = patientId;
            this.doctorId = doctorId;
        }
        public AppointmentSummaryBuilder addSummary(String summary){
            this.summary = summary;
            return this;
        }
        public AppointmentSummary build(){

            return new AppointmentSummary(this);
        }
    }
}
