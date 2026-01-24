package models;

public abstract class user {
    protected int id;
    protected String name;
    protected String email;
    protected String phone;
    protected String password;

    protected user(String name, String email, String phone, String password){
        setName(name);
        setEmail(email);
        setPhone(phone);
        setPassword(password);
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
    public String getPassword(){ return password;}
    public void setId(){
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
}
