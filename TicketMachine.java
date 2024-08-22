import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class TicketMachine {

    private List<Destination> destinations;
    private List<PaymentMethod> paymentMethods;

    public TicketMachine() {
        this.destinations = new ArrayList<>();
        this.paymentMethods = new ArrayList<>();
        loadDestinations();
        loadPaymentMethods();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Ticket Machine!");

        // Display destinations
        System.out.println("Available Destinations:");
        for (int i = 0; i < destinations.size(); i++) {
            System.out.println((i + 1) + ". " + destinations.get(i).getName() + " - $" + destinations.get(i).getPrice());
        }

        // Select destination
        System.out.print("Select a destination (1-" + destinations.size() + "): ");
        int destinationIndex = scanner.nextInt() - 1;
        Destination selectedDestination = destinations.get(destinationIndex);

        // Select payment method
        System.out.println("Select Payment Method:");
        System.out.println("1. Cash");
        System.out.println("2. Card");
        int paymentMethodIndex = scanner.nextInt() - 1;
        PaymentMethod paymentMethod = paymentMethods.get(paymentMethodIndex);

        // Process payment
        boolean paymentSuccessful = paymentMethod.processPayment(selectedDestination.getPrice());
        if (paymentSuccessful) {
            // Issue ticket and receipt
            Ticket ticket = new Ticket(selectedDestination.getName(), selectedDestination.getPrice());
            printTicket(ticket);
            Receipt receipt = new Receipt(ticket);
            receipt.printReceipt();
        } else {
            System.out.println("Payment failed. Please try again.");
        }

        scanner.close();
    }

    private void loadDestinations() {
        destinations.add(new Destination("Central Station", 2.50));
        destinations.add(new Destination("East Side", 3.00));
        destinations.add(new Destination("West End", 4.00));
    }

    private void loadPaymentMethods() {
        paymentMethods.add(new CashPayment());
        paymentMethods.add(new CardPayment());
    }

    public void printTicket(Ticket ticket) {
        System.out.println("Ticket:");
        System.out.println("Destination: " + ticket.getDestination());
        System.out.println("Price: $" + ticket.getPrice());
        System.out.println("Date: " + ticket.getIssueDate());
        System.out.println("-----------");
    }

    public static void main(String[] args) {
        TicketMachine ticketMachine = new TicketMachine();
        ticketMachine.start();
    }

    class Ticket {
        private String destination;
        private double price;
        private Date issueDate;

        public Ticket(String destination, double price) {
            this.destination = destination;
            this.price = price;
            this.issueDate = new Date();
        }

        public String getDestination() {
            return destination;
        }

        public double getPrice() {
            return price;
        }

        public Date getIssueDate() {
            return issueDate;
        }
    }

    class Destination {
        private String name;
        private double price;

        public Destination(String name, double price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }
    }

    interface PaymentMethod {
        boolean processPayment(double amount);
    }

    class CashPayment implements PaymentMethod {
        public boolean processPayment(double amount) {
            System.out.println("Processing cash payment of $" + amount);
            return true;
        }
    }

    class CardPayment implements PaymentMethod {
        public boolean processPayment(double amount) {
            System.out.println("Processing card payment of $" + amount);
            return true;
        }
    }

    class Receipt {
        private Ticket ticket;
        private Date purchaseDate;

        public Receipt(Ticket ticket) {
            this.ticket = ticket;
            this.purchaseDate = new Date();
        }

        public void printReceipt() {
            System.out.println("Receipt:");
            System.out.println("Destination: " + ticket.getDestination());
            System.out.println("Price: $" + ticket.getPrice());
            System.out.println("Date: " + purchaseDate);
            System.out.println("-----------");
        }
    }
}
