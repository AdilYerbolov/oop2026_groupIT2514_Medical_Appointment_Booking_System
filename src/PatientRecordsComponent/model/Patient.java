package PatientRecordsComponent.model;

public class Patient extends User {

    public Patient(String name, String email, String phone, String password){
        super(name, email, phone, password);
    }

    @Override
    public String toString(){
        return "patient name: " + name + ", email: " + email + ", phone: " + phone + "\n";
    }
}
