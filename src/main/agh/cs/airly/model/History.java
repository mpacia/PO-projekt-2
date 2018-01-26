package agh.cs.airly.model;

import java.util.Date;

public class History {
    private Date fromDateTime;
    private Date tillDateTime;
    private Measurements measurements;

    public Measurements getMeasurements() {
        return measurements;
    }

    public Date getFromDateTime() {
        return fromDateTime;
    }

    public Date getTillDateTime() {
        return tillDateTime;
    }
}
