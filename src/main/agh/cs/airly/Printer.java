package agh.cs.airly;

import agh.cs.airly.model.History;
import agh.cs.airly.model.Measurement;
import agh.cs.airly.model.Measurements;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static java.lang.System.*;

public class Printer {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm", Locale.US);

    private Measurement measurement;

    public Printer(Measurement measurement) {
        this.measurement = measurement;
    }

    public void printCurrent() {
        Measurements measurements = measurement.getCurrentMeasurements();

        int caqi = (int) Math.round(measurements.getAirQualityIndex());
        int pm25 = (int) Math.round(measurements.getPm25());
        int pm10 = (int) Math.round(measurements.getPm10());

        int pm25Percent = (int) Math.round(measurements.getPm25() * 100 / 25);
        int pm10Percent = (int) Math.round(measurements.getPm25() * 100 / 50);

        int temperature = (int) Math.round(measurements.getTemperature());
        int pressure = (int) Math.round(measurements.getPressure() / 100);
        int humidity = (int) Math.round(measurements.getHumidity());

        String airQuality = "";
        switch (getCaqiLevel(measurements.getAirQualityIndex())) {
            case 1:
                airQuality = "Very good";
                break;
            case 2:
                airQuality = "Good";
                break;
            case 3:
                airQuality = "Average";
                break;
            case 4:
                airQuality = "Bad";
                break;
            case 5:
                airQuality = "Terrible";
                break;
        }

        out.println(String.format("" +
                        "Current measurements: \n" +
                        "------------------------------------------------------------------------------\n" +
                        "| CAQI: %3d              | PM2.5: %3d μg/m3  %3d%%    | PM10: %3d μg/m3  %3d%% |\n" +
                        "------------------------------------------------------------------------------\n" +
                        "| Temperature: %3d°C     | Pressure: %4d hPa        | Humidity: %3d%%        |\n" +
                        "------------------------------------------------------------------------------\n" +
                        "| Air quality: %10s                                                    |\n" +
                        "------------------------------------------------------------------------------\n",

                caqi, pm25, pm25Percent, pm10, pm10Percent,
                temperature, pressure, humidity,
                airQuality));
    }

    public void printHistory() {
        out.println("History:");
        out.println("------------------------------------------------------------------------------");

        for (History historyData : measurement.getHistory()) {
            Measurements measurements = historyData.getMeasurements();
            LocalDateTime dateTime = historyData.getFromDateTime()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();

            int caqi = (int) Math.round(measurements.getAirQualityIndex());
            int pm25 = (int) Math.round(measurements.getPm25());
            int pm10 = (int) Math.round(measurements.getPm10());

            out.println(String.format("|  %16s  |  CAQI: %3d  |  PM2.5: %3d μg/m3  |  PM10: %3d μg/m3   |",
                    DATE_FORMATTER.format(dateTime), caqi, pm25, pm10));
            out.println("------------------------------------------------------------------------------");
        }
    }

    private int getCaqiLevel(double airQuality) {
        if (airQuality > 100)
            return 5;
        if (airQuality > 75)
            return 4;
        if (airQuality > 50)
            return 3;
        if (airQuality > 25)
            return 2;
        else
            return 1;
    }
}
