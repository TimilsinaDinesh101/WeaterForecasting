import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;

public class WeatherApp {
    private static String API_KEY;

    public static void main(String[] args) throws Exception {

        try {
            API_KEY = getAPIKey();
            System.out.println("API Key: " + API_KEY);
        } catch (IOException e) {
            System.out.println("Error: Unable to load API Key.");
            e.printStackTrace();
        }
        // gets the user input
        System.out.println("Enter a city name: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String cityName;
        try {
            cityName = reader.readLine();
            if (cityName.isEmpty()) {
                System.out.println("cityName cannot be empty");
            }
            String weatherData = fetchWeatherData(cityName);
            System.out.println("Weather Data for : " + cityName + " " + weatherData);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static String getAPIKey() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(".env"));
        return properties.getProperty("API_KEY");
    }

    private static String fetchWeatherData(String cityName) throws Exception {

        // construct the API URL using city name and API key
        String urlString = "http://api.openweathermap.org/data/2.5/weather?q="
                + cityName + "&appid=" + API_KEY + "&unit=metrics";

        // Create a HTTP client instance
        HttpClient client = HttpClient.newHttpClient();

        // Build the request using uri and get method
        HttpRequest request = HttpRequest.newBuilder().uri(new URI(urlString)).GET().build();

        // send the request and get the response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();

    }
}
