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

public class Customer {

    Logger logger = Logger.getLogger(Customer.class.getName());

    RestTemplate restTemplate = new RestTemplate();

    public void buyTickets() {

        String apiUrl = "http://localhost:8090/api/customer/buy-tickets";

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the event name: ");
        String eventName = scanner.nextLine();

        int count;
        while (true) {
            System.out.println("Enter the number of tickets to buy: ");
            if (scanner.hasNextInt()) {
                count = scanner.nextInt();
                if (count > 0) {
                    break;
                } else {
                    System.out.println("Please enter a positive number.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }

        Map<String, Object> requestBody = new HashMap<>();

        requestBody.put("eventName", eventName);
        requestBody.put("count", count);

        //Create HTTP headers
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
