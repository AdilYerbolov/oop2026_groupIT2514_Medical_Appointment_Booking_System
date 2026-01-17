package entity;

import java.time.LocalTime;

public class Doctor {
    private int id;
    private LocalTime availableFrom;
    private LocalTime availableTo;

    public Doctor(int id, LocalTime from, LocalTime to) {
        this.id = id;
        this.availableFrom = from;
        this.availableTo = to;
    }

    public int getId() {
        return id;
    }

    public LocalTime getAvailableFrom() {
        return availableFrom;
    }

    public LocalTime getAvailableTo() {
        return availableTo;
    }
}
