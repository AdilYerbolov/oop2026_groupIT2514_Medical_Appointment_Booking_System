package models;

public class patient extends user{

    public patient(String name, String email, String phone, String password){
        super(name, email, phone, password);
    }

    @Override
    public String toString(){
        return "patient name: " + name + ", email: " + email + ", phone: " + phone + "\n";
    }
}
