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

public class Vendor implements Runnable{

    private Long eventId;
    private int count;
    private String apiUrl;

    public Vendor() {
    }

    public Vendor(Long eventId, int count, String apiUrl) {
        this.eventId = eventId;
        this.count = count;
        this.apiUrl = apiUrl;
    }

    Logger logger = Logger.getLogger(Vendor.class.getName());

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

    public void addTickets(){

        try {

            int numberOfVendors;
            while(true){
                System.out.print("Enter number of vendors: ");
                if(scanner.hasNextInt()){
                    numberOfVendors = scanner.nextInt();
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.next();
                }
            }

            for (int i = 0; i < numberOfVendors; i++) {
                System.out.println("For Vendor " + (i + 1));

                Long eventId;
                while(true){
                    System.out.print("Enter the event id: ");
                    if(scanner.hasNextLong()){
                        eventId = scanner.nextLong();
                        break;
                    } else {
                        System.out.println("Invalid input. Please enter a number.");
                        scanner.next();
                    }
                }

                int count;
                while(true){
                    System.out.print("Enter the number of tickets to add: ");
                    if(scanner.hasNextInt()){
                        count = scanner.nextInt();
                        break;
                    } else {
                        System.out.println("Invalid input. Please enter a number.");
                        scanner.next();
                    }
                }

                String apiUrl = "http://localhost:8090/api/vendor/add-tickets";
                Vendor vendor = new Vendor(eventId, count, apiUrl);
                Thread vendorThread = new Thread(vendor);
                vendorThread.start();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to add tickets. Please try again.", e);
        }
    }

}
