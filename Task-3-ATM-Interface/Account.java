import java.util.ArrayList;

public class Account {

    private String userId;
    private String pin;
    private String holderName;
    private double balance;

    private ArrayList<Transaction> transactions;

    public Account(String userId, String pin,
                   String holderName, double balance) {

        this.userId = userId;
        this.pin = pin;
        this.holderName = holderName;
        this.balance = balance;

        transactions = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public String getHolderName() {
        return holderName;
    }

    public double getBalance() {
        return balance;
    }

    public boolean authenticate(String enteredPin) {
        return pin.equals(enteredPin);
    }

    public boolean withdraw(double amount) {

        if (amount <= 0 || amount > balance) {
            return false;
        }

        balance -= amount;
        return true;
    }

    public boolean deposit(double amount) {

        if (amount <= 0) {
            return false;
        }

        balance += amount;
        return true;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public void showTransactionHistory() {

        if (transactions.isEmpty()) {
            System.out.println("No transactions available.");
            return;
        }

        System.out.println("\n------ TRANSACTION HISTORY ------");

        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }
}