package agh.cs.airly.model;

import java.util.ArrayList;
import java.util.List;

public class Measurement {
    private Measurements currentMeasurements;
    private List<History> history = new ArrayList<>();

    public Measurements getCurrentMeasurements() {
        return currentMeasurements;
    }

    public List<History> getHistory() {
        return history;
    }
}
