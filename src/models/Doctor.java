package models;

import java.time.LocalTime;

public class Doctor {
    private int id;
    private String name;
    private LocalTime availableFrom;
    private LocalTime availableTo;

    public Doctor(int id, String name, LocalTime availableFrom, LocalTime availableTo) {
        this.id = id;
        this.name = name;
        this.availableFrom = availableFrom;
        this.availableTo = availableTo;
    }

    public int getId() { return id;}
    public String getName() { return name;}
    public LocalTime getAvailableFrom() { return availableFrom; }
    public LocalTime getAvailableTo() { return availableTo; }
}