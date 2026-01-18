package models;

public class patient {
    private int id;
    private String name;
    private String email;
    private String phone;

    public patient(String name, String email, String phone){
        setEmail(email);
        setName(name);
        setPhone(phone);
    }

    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }
    public String getPhone(){
        return phone;
    }
    public void setId(int id){
        this.id = id;
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
    public void setEmail(String email){
        if (email != null){
            if (!email.isEmpty()){
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
        if (phone != null){
            if (!phone.isEmpty()){
                this.phone = phone;
            }
            else{
                throw new IllegalArgumentException("phone must not be empty!");
            }
        }
        else{
            throw new IllegalArgumentException("phone must not be empty!");
        }
    }
}
