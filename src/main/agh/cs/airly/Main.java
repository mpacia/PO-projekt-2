package agh.cs.airly;

import agh.cs.airly.model.Measurement;
import com.google.gson.Gson;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import static java.lang.System.*;

public class Main {

    public static void main(String[] args) {
        Options options = new Options()
                .addOption(null, "history", false, "")
                .addOption(null, "api-key", true, "")
                .addOption(null, "latitude", true, "")
                .addOption(null, "longitude", true, "")
                .addOption(null, "sensor-id", true, "");

        CommandLine cmd;
        try {
            cmd = new DefaultParser().parse(options, args);
        } catch (ParseException e) {
            out.println("Invalid arguments");
            return;
        }

        String apiKey = cmd.getOptionValue("api-key");
        if (apiKey == null) {
            apiKey = System.getenv("API_KEY");
        }
        String sensorIdText = cmd.getOptionValue("sensor-id");
        String latitudeText = cmd.getOptionValue("latitude");
        String longitudeText = cmd.getOptionValue("longitude");
        boolean history = cmd.hasOption("history");

        Integer sensorId = null;
        Double latitude = null;
        Double longitude = null;

        try {
            if (sensorIdText != null)
                sensorId = Integer.parseUnsignedInt(sensorIdText);
            if (latitudeText != null)
                latitude = Double.parseDouble(latitudeText);
            if (longitudeText != null)
                longitude = Double.parseDouble(longitudeText);
        } catch (NumberFormatException e) {
            out.println("Invalid arguments");
            return;
        }

        if (apiKey == null) {
            out.println("Missing api key");
            return;
        }

        Query query;
        if (sensorId != null && latitude == null && longitude == null) {
            query = Query.withSensorId(apiKey, sensorId, history);
        } else if (sensorId == null && latitude != null && longitude != null) {
            query = Query.withCoords(apiKey, longitude, latitude, history);
        } else {
            err.println("Missing sensor id or latitude & longitude");
            return;
        }

        Measurement measurement;
        try {
            String response = query.execute();
            measurement = new Gson().fromJson(response, Measurement.class);
        } catch (Exception e) {
            out.println(e.getMessage());
            return;
        }

        Printer printer = new Printer(measurement);
        printer.printCurrent();
        if (history)
            printer.printHistory();
    }
}
