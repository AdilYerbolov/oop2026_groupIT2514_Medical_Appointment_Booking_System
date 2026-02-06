package PatientRecordsComponent.service;

import PatientRecordsComponent.model.Doctor;
import PatientRecordsComponent.model.Patient;
import PatientRecordsComponent.repository.doctorDatabase;
import PatientRecordsComponent.repository.patientsDatabase;

import java.sql.SQLException;

public class PatientService {
    doctorDatabase doctorDatabase = new doctorDatabase();
    patientsDatabase patientsDatabase = new patientsDatabase();

    private static final PatientService INSTANCE = new PatientService();
    private PatientService(){}
    public static PatientService getInstance(){return INSTANCE;}
    public boolean isUserVerified(String email, String password, boolean isDoctor){
        String dbPassword;
        if (!isDoctor) {
            try {
                dbPassword = patientsDatabase.getUserPasswordByEmail(email);
            } catch (SQLException e){
                System.out.println("Database error: ");
                e.printStackTrace();
                return false;
            }
        }
        else{
            try {
                dbPassword = doctorDatabase.getUserPasswordByEmail(email);
            } catch (SQLException e){
                System.out.println("Database error: ");
                e.printStackTrace();
                return false;
            }
        }
        if (dbPassword.equals(password)){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean isPatientCreated (Patient user){
        try {
            patientsDatabase.insertUser(user);
            return true;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    public boolean isDoctorCreated(Doctor user){
        try {
            doctorDatabase.insertUser(user);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public int getUserId(String email, boolean isDoctor){
        if (!isDoctor){
            try {
                int id = patientsDatabase.getId(email);
                return id;
            } catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
        else {
            try {
                int id = doctorDatabase.getId(email);
                return id;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
