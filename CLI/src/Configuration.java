import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Configuration {

    Logger logger = Logger.getLogger(Configuration.class.getName());

    RestTemplate restTemplate = new RestTemplate();

    String apiUrl = "http://localhost:8090/api/config/add";

    public void addConfiguration() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the event name: ");
        String eventName = scanner.nextLine();

        System.out.println("Enter the location: ");
        String location = scanner.nextLine();

        System.out.println("Enter the total number of tickets: ");
        int no_of_tickets = scanner.nextInt();

        System.out.println("Enter the ticket release rate: ");
        int ticket_release_rate = scanner.nextInt();

        System.out.println("Enter the customer retrieval rate: ");
        int customer_retrieval_rate = scanner.nextInt();

        System.out.println("Enter the maximum number of tickets in ticket pool: ");
        int max_tickets = scanner.nextInt();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("eventName", eventName);
        requestBody.put("location", location);
        requestBody.put("no_of_tickets", no_of_tickets);
        requestBody.put("ticket_release_rate", ticket_release_rate);
        requestBody.put("customer_retrieval_rate", customer_retrieval_rate);
        requestBody.put("max_tickets", max_tickets);

        // Create HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            // Send the POST request and get the response
            Map<String, Object> response = restTemplate.exchange(apiUrl, HttpMethod.POST, request, Map.class).getBody();

            // Display the response in the CLI
            if (response != null) {
                System.out.println("Response from backend:");
                System.out.println("Message: " + response.get("message"));
                System.out.println("Status: " + response.get("status"));
            } else {
                System.out.println("No response received from backend.");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to connect to the backend. Ensure the API is running.", e);
        }
    }
}
