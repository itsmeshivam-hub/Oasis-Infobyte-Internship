import java.util.Scanner;

public class ATM {

    private Bank bank;
    private Scanner sc;

    public ATM(Bank bank) {
        this.bank = bank;
        this.sc = new Scanner(System.in);
    }

    public void start() {

        System.out.println("================================");
        System.out.println("       WELCOME TO ATM");
        System.out.println("================================");

        Account account = login();

        if (account == null) {
            System.out.println("Access Denied!");
            return;
        }

        System.out.println("\nWelcome, " + account.getHolderName());

        boolean running = true;

        while (running) {

            System.out.println("\n========== ATM MENU ==========");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Enter your choice: ");

            int choice = readInt();

            switch (choice) {

                case 1:
                    account.showTransactionHistory();
                    break;

                case 2:
                    withdraw(account);
                    break;

                case 3:
                    deposit(account);
                    break;

                case 4:
                    transfer(account);
                    break;

                case 5:
                    System.out.println("Thank you for using our ATM!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private Account login() {

        int attempts = 3;

        while (attempts > 0) {

            System.out.print("\nEnter User ID: ");
            String userId = sc.nextLine();

            System.out.print("Enter PIN: ");
            String pin = sc.nextLine();

            Account account = bank.getAccount(userId);

            if (account != null && account.authenticate(pin)) {
                System.out.println("\nLogin Successful!");
                return account;
            }

            attempts--;

            System.out.println("Invalid User ID or PIN!");

            if (attempts > 0) {
                System.out.println("Attempts remaining: " + attempts);
            }
        }

        System.out.println("Maximum attempts exceeded.");
        return null;
    }

    private void withdraw(Account account) {

        System.out.print("Enter withdrawal amount: Rs. ");
        double amount = readDouble();

        if (amount <= 0) {
            System.out.println("Enter a valid amount.");
            return;
        }

        if (account.withdraw(amount)) {

            account.addTransaction(
                    new Transaction("Withdrawal",
                            "Cash withdrawn", amount));

            System.out.println("Withdrawal Successful!");
            System.out.println("Remaining Balance: Rs. "
                    + account.getBalance());

        } else {
            System.out.println("Insufficient Funds!");
        }
    }

    private void deposit(Account account) {

        System.out.print("Enter deposit amount: Rs. ");
        double amount = readDouble();

        if (account.deposit(amount)) {

            account.addTransaction(
                    new Transaction("Deposit",
                            "Cash deposited", amount));

            System.out.println("Deposit Successful!");
            System.out.println("Updated Balance: Rs. "
                    + account.getBalance());

        } else {
            System.out.println("Invalid deposit amount!");
        }
    }

    private void transfer(Account sender) {

        System.out.print("Enter recipient Account ID: ");
        String recipientId = sc.nextLine();

        Account recipient = bank.getAccount(recipientId);

        if (recipient == null) {
            System.out.println("Recipient account not found!");
            return;
        }

        if (recipient == sender) {
            System.out.println("Cannot transfer to your own account!");
            return;
        }

        System.out.print("Enter transfer amount: Rs. ");
        double amount = readDouble();

        if (amount <= 0) {
            System.out.println("Enter a valid amount.");
            return;
        }

        if (sender.getBalance() < amount) {
            System.out.println("Insufficient Funds!");
            return;
        }

        sender.withdraw(amount);
        recipient.deposit(amount);

        sender.addTransaction(
                new Transaction("Transfer",
                        "Transferred to " + recipientId, amount));

        recipient.addTransaction(
                new Transaction("Received",
                        "Received from " + sender.getUserId(), amount));

        System.out.println("Transfer Successful!");
        System.out.println("Remaining Balance: Rs. "
                + sender.getBalance());
    }

    private int readInt() {

        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Enter a valid menu number: ");
            }
        }
    }

    private double readDouble() {

        while (true) {
            try {
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Enter a valid amount: Rs. ");
            }
        }
    }
}