package nintendods.ei.fti.uantwerpen.bankapplication;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BankClient {

    private static final String BASE_URL = "http://localhost:8080"; // Assuming the server is running on localhost:8080

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Simultaneously perform operations on the joint bank account
        executor.submit(() -> {
            try {
                performOperations("Client 1", 100);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        executor.submit(() -> {
            try {
                performOperations("Client 2", 50);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        executor.shutdown();
    }

    private static void performOperations(String clientName, int amount) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        // Add money
        HttpRequest addRequest = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/add"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(String.valueOf(amount)))
                .build();

        HttpResponse<String> addResponse = client.send(addRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println(clientName + " added $" + amount + ". New balance: " + addResponse.body());

        // Get balance
        HttpRequest balanceRequest = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/balance"))
                .build();

        HttpResponse<String> balanceResponse = client.send(balanceRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println(clientName + " balance: $" + balanceResponse.body());

        // Remove money
        HttpRequest removeRequest = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/remove"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(String.valueOf(amount / 2)))
                .build();

        HttpResponse<String> removeResponse = client.send(removeRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println(clientName + " removed $" + (amount / 2) + ". New balance: " + removeResponse.body());

        // Get money
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/get"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(String.valueOf(amount / 4)))
                .build();

        HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println(clientName + " withdrew $" + (amount / 4) + ". Amount received: $" + getResponse.body());

        // Get final balance
        balanceResponse = client.send(balanceRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println(clientName + " final balance: $" + balanceResponse.body());
    }
}
