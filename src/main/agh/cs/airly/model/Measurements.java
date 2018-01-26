package agh.cs.airly.model;

public class Measurements {
    private double airQualityIndex;
    private double humidity;
    private double pm1;
    private double pm25;
    private double pm10;
    private double pressure;
    private double temperature;
    private int pollutionLevel;

    public Measurements(double airQualityIndex,
                        double humidity,
                        double pm1,
                        double pm25,
                        double pm10,
                        double pressure,
                        double temperature,
                        int pollutionLevel) {
        this.airQualityIndex = airQualityIndex;
        this.humidity = humidity;
        this.pm1 = pm1;
        this.pm25 = pm25;
        this.pm10 = pm10;
        this.pressure = pressure;
        this.temperature = temperature;
        this.pollutionLevel = pollutionLevel;
    }

    public double getAirQualityIndex() {
        return airQualityIndex;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getPm1() {
        return pm1;
    }

    public double getPm25() {
        return pm25;
    }

    public double getPm10() {
        return pm10;
    }

    public double getPressure() {
        return pressure;
    }

    public double getTemperature() {
        return temperature;
    }

    public int getPollutionLevel() {
        return pollutionLevel;
    }
}
