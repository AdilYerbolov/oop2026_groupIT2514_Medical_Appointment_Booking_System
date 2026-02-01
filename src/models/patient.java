package models;

public class Patient {
    private int id;
    private String name;
    private String email;

    public Patient(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() { return id;}
    public String getName() { return name;}
}
