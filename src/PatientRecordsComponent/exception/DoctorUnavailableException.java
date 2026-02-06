package PatientRecordsComponent.exception;

public class DoctorUnavailableException extends RuntimeException {
    public DoctorUnavailableException(String message) {
        super(message);
    }
}
