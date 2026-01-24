package models;

public class doctor extends user{
    private String specialization;
    private String avFrom;
    private String avTo;

    doctor(String name, String email, String phone, String specilizationation, String avFrom, String avTo, String password){
        super(name, email, phone, password);
        setSpecialization(specilizationation);
        setAvFrom(avFrom);
        setAvTo(avTo);
    }

    public String getSpecialization(){
        return specialization;
    }
    public String getEmail(){
        return email;
    }
    public String getPhone() {
        return phone;
    }
    public String getPassword() {
        return password;
    }
    public String getAvFrom(){
        return avFrom;
    }
    public String getAvTo(){
        return avTo;
    }
    public void setName(String name){
        if (name != null){
            if (!name.isEmpty()){
                this.name = name;
            }
            else{
                throw new IllegalArgumentException("name must not be empty!");
            }
        }
        else{
            throw new IllegalArgumentException("name must not be empty!");
        }
    }
    public void setEmail(String email) {
        if (email != null) {
            if (!email.isEmpty()) {
                this.email = email;
            }
            else{
                throw new IllegalArgumentException("email must not be empty!");
            }
        }
        else{
            throw new IllegalArgumentException("email must not be empty!");
        }
    }
    public void setPhone(String phone){
        if (phone != null) {
            if (!phone.isEmpty()) {
                this.phone= phone;
            }
            else{
                throw new IllegalArgumentException("phone must not be empty!");
            }
        }
        else{
            throw new IllegalArgumentException("phone must not be empty!");
        }
    }
    public void setSpecialization(String specification){
        if (specification != null){
            if (!specification.isEmpty()){
                this.specialization = specification;
            }
            else{
                throw new IllegalArgumentException("specialization must not be empty!");
            }
        }
        else{
            throw new IllegalArgumentException("specialization must not be empty!");
        }
    }

    public void setAvFrom(String avFrom) {
        this.avFrom = avFrom;
    }
    public void setAvTo(String avTo){
        this.avTo = avTo;
    }
    public void setPassword(String password){
        if (password != null) {
            if (!password.isEmpty()) {
                this.password = email;
            }
            else{
                throw new IllegalArgumentException("password must not be empty!");
            }
        }
        else{
            throw new IllegalArgumentException("password must not be empty!");
        }
    }

    @Override
    public String toString(){
        return "Doctor name: " + name + ", specification: " + specialization + ", available from: " + avFrom + " to " + avTo;
    }
}
