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

    public void addConfiguration() {

        String apiUrl = "http://localhost:8090/api/config/add";

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the event name: ");
        String eventName = scanner.nextLine();

        System.out.println("Enter the location: ");
        String location = scanner.nextLine();


        int no_of_tickets;
        while (true){
            System.out.println("Enter the total number of tickets: ");
            if(scanner.hasNextInt()){
                no_of_tickets = scanner.nextInt();
                if(no_of_tickets > 0){
                    break;
                } else {
                    System.out.println("Please enter a positive number.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }

        int ticket_release_rate;
        while (true) {
            System.out.println("Enter the ticket release rate: ");
            if (scanner.hasNextInt()) {
                ticket_release_rate = scanner.nextInt();
                if (ticket_release_rate > 0) {
                    break;
                } else {
                    System.out.println("Please enter a positive number.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }

        int customer_retrieval_rate;
        while(true){
            System.out.println("Enter the customer retrieval rate: ");
            if(scanner.hasNextInt()){
                customer_retrieval_rate = scanner.nextInt();
                if(customer_retrieval_rate > 0){
                    break;
                } else {
                    System.out.println("Please enter a positive number.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }

        int max_tickets;
        while(true){
            System.out.println("Enter the maximum number of tickets in ticket pool: ");
            if(scanner.hasNextInt()){
                max_tickets = scanner.nextInt();
                if(max_tickets > 0){
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
