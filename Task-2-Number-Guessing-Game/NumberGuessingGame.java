
import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        int round = 1;
        int totalScore = 0;
        String playAgain;

        System.out.println("================================");
        System.out.println("     NUMBER GUESSING GAME");
        System.out.println("================================");

        do {
            System.out.println("\nSelect Difficulty:");
            System.out.println("1. Easy   (1-50, 10 attempts)");
            System.out.println("2. Medium (1-100, 7 attempts)");
            System.out.println("3. Hard   (1-200, 5 attempts)");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();

            int maxNumber;
            int maxAttempts;

            switch (choice) {
                case 1:
                    maxNumber = 50;
                    maxAttempts = 10;
                    break;

                case 2:
                    maxNumber = 100;
                    maxAttempts = 7;
                    break;

                case 3:
                    maxNumber = 200;
                    maxAttempts = 5;
                    break;

                default:
                    System.out.println("Invalid choice! Medium selected.");
                    maxNumber = 100;
                    maxAttempts = 7;
            }

            int secretNumber = random.nextInt(maxNumber) + 1;
            int attempts = 0;
            boolean guessed = false;

            System.out.println("\nRound " + round);
            System.out.println("Guess a number between 1 and "
                    + maxNumber);
            System.out.println("Maximum attempts: " + maxAttempts);

            while (attempts < maxAttempts) {

                System.out.print("\nEnter your guess: ");
                int guess = sc.nextInt();
                attempts++;

                System.out.println("Attempt: " + attempts
                        + "/" + maxAttempts);

                if (guess < 1 || guess > maxNumber) {
                    System.out.println("Enter a number within the range!");
                } else if (guess > secretNumber) {
                    System.out.println("Too High!");
                } else if (guess < secretNumber) {
                    System.out.println("Too Low!");
                } else {
                    System.out.println("Correct!");
                    guessed = true;
                    totalScore++;

                    System.out.println("Round " + round
                            + " — guessed in " + attempts
                            + " attempts.");
                    break;
                }
            }

            if (!guessed) {
                System.out.println("\nYou Lost!");
                System.out.println("The correct number was: "
                        + secretNumber);
                System.out.println("Round " + round
                        + " — not guessed in "
                        + maxAttempts + " attempts.");
            }

            System.out.println("\nRounds completed: " + round);
            System.out.println("Rounds won: " + totalScore);

            System.out.print("\nPlay Again? (yes/no): ");
            playAgain = sc.next();

            round++;

        } while (playAgain.equalsIgnoreCase("yes"));

        System.out.println("\n================================");
        System.out.println("        GAME OVER");
        System.out.println("Total rounds played: " + (round - 1));
        System.out.println("Total rounds won: " + totalScore);
        System.out.println("Thanks for playing!");
        System.out.println("================================");

        sc.close();
    }
}
