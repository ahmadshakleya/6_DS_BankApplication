package nintendods.ei.fti.uantwerpen.bankapplication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentClient {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Submit two tasks to the executor
        executor.submit(() -> sendRequest());
        executor.submit(() -> sendRequest());

        // Shutdown the executor after the tasks are completed
        executor.shutdown();
    }

    private static void sendRequest() {
        try {
            // Create URL object with the endpoint
            URL url = new URL("http://localhost:8081/add?bankNumber=BE12%201234%201234&amount=500");
            // Open connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // Set request method
            conn.setRequestMethod("POST");
            // Set request headers
            conn.setRequestProperty("Content-Type", "application/json");
            // Get the response code
            int responseCode = conn.getResponseCode();

            // Read the response from the server
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Print the response
            System.out.println("Response Code : " + responseCode);
            System.out.println("Response : " + response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
