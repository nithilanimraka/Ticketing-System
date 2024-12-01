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

public class Customer implements Runnable{

    private Long eventId;
    private int count;
    private String apiUrl;

    public Customer() {
    }

    public Customer(Long eventId, int count, String apiUrl) {
        this.eventId = eventId;
        this.count = count;
        this.apiUrl = apiUrl;
    }

    Logger logger = Logger.getLogger(Customer.class.getName());

    Scanner scanner = new Scanner(System.in);
    RestTemplate restTemplate = new RestTemplate();

    public void run() {
        try {

            Map<String, Object> requestBody = new HashMap<>();

            requestBody.put("configId", eventId);
            requestBody.put("count", count);

            //Create HTTP headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

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

    public void buyTickets(){
        try {
            System.out.print("Enter number of customers: ");
            int numberOfCustomers = scanner.nextInt();

            for (int i = 0; i < numberOfCustomers; i++) {
                System.out.println("For Customer " + (i + 1));
                System.out.print("Enter the event id: ");
                Long eventId = scanner.nextLong();
                System.out.print("Enter the number of tickets to buy: ");
                int count = scanner.nextInt();
                String apiUrl = "http://localhost:8090/api/customer/buy-tickets";
                Customer customer = new Customer(eventId, count, apiUrl);
                Thread customerThread = new Thread(customer);
                customerThread.start();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to buy tickets. Please try again.", e);
        }
    }

}
