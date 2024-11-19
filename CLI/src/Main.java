import java.util.Scanner;
import java.util.logging.Logger;
import  java.util.logging.Level;

public class Main {
    public static void main(String[] args) {

        Logger logger = Logger.getLogger(Main.class.getName());
        boolean condition = true;

        while(condition){
            Scanner scanner = new Scanner(System.in);

            System.out.println("----------------------MENU----------------------");
            System.out.println("1. Add a new configuration");
            System.out.println("2. Buy tickets");
            System.out.println("3. Add tickets");
            System.out.println("4. Exit");
            System.out.println("-------------------------------------------------");

            System.out.println("Please choose an option(1,2,3 or 4):");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    Configuration configuration = new Configuration();
                    configuration.addConfiguration();
                    break;
                case 2:
                    Customer customer = new Customer();
                    customer.buyTickets();
                    break;
                case 3:
                    Vendor vendor = new Vendor();
                    vendor.addTickets();
                    break;
                case 4:
                    condition = false;
                    break;

                default:
                    System.out.println("Invalid choice. Please select 1, 2, or 3.");
                    break;
            }
        }



    }
}