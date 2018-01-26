package agh.cs.airly;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Query {
    private String url;

    public static Query withSensorId(String apikey, Integer sensorID, boolean history) {
        return new Query(makeQueryUrl(apikey, null, null, sensorID, history));
    }

    public static Query withCoords(String apikey, Double longitude, Double latitude, boolean history) {
        return new Query(makeQueryUrl(apikey, latitude, longitude, null, history));
    }

    private Query(String url) {
        this.url = url;
    }

    public static String makeQueryUrl(String apikey,
                                      Double latitude, Double longitude,
                                      Integer sensorID,
                                      boolean history) {
        String url = "https://airapi.airly.eu/v1/";

        if (sensorID != null) {
            url += "sensor/measurements?sensorId=" + sensorID
                    + "&apikey=" + apikey;
        } else {
            url += "mapPoint/measurements?latitude=" + latitude
                    + "&longitude=" + longitude
                    + "&apikey=" + apikey;
        }

        if (history) {
            url += "&history";
        }

        return url;
    }

    public String execute() throws IOException, AirlyApiKeyException {
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("GET");
        int respondCode = connection.getResponseCode();
        if (respondCode == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } else {
            String message = "";
            if (respondCode == 403) {
                message = "Error 403. Api-key not found";
            } else if (respondCode == 400) {
                message = "Error 400. Input validation error";
            }
            throw new AirlyApiKeyException(message);
        }
    }
}
