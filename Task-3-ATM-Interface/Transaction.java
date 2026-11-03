import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

    private String type;
    private String details;
    private double amount;
    private LocalDateTime date;

    public Transaction(String type, String details, double amount) {

        this.type = type;
        this.details = details;
        this.amount = amount;
        this.date = LocalDateTime.now();
    }

    @Override
    public String toString() {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        return "Type: " + type +
                " | Details: " + details +
                " | Amount: Rs. " + amount +
                " | Date: " + date.format(formatter);
    }
}