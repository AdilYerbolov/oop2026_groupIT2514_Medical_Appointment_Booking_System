package models;

public class doctor {
    private int id;
    private String name;
    private String specialization;
    private String avFrom;
    private String avTo;

    doctor(String name, String specilizationation, String avFrom, String avTo){
        setName(name);
        setSpecialization(specilizationation);
        setAvFrom(avFrom);
        setAvTo(avTo);
    }

    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getSpecialization(){
        return specialization;
    }
    public String getAvFrom(){
        return avFrom;
    }
    public String getAvTo(){
        return avTo;
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

    @Override
    public String toString(){
        return "Doctor name: " + name + ", specification: " + specialization + ", available from: " + avFrom + " to " + avTo;
    }
}
