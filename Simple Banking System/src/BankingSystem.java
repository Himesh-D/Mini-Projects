
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
public class BankingSystem {
    private static ArrayList<BankAccount> accounts = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);
    private static final String FILE_NAME = "data/accounts.txt";

    public static void main(String[] args) {
        loadAccountsFromFile();  // Load existing accounts
        
        int choice;
        do {
            System.out.println("\n=== Simple Banking System ===");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Check Balance");
            System.out.println("5. Display All Accounts");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    depositMoney();
                    break;
                case 3:
                    withdrawMoney();
                    break;
                case 4:
                    checkBalance();
                    break;
                case 5:
                    displayAllAccounts();
                    break;
                case 6:
                    System.out.println("Thank you for using the banking system!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 6);
    }

    private static void createAccount() {
        System.out.print("Enter Account Holder Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Initial Deposit Amount: ");
        double deposit = sc.nextDouble();

        int accNumber = generateAccountNumber();
        BankAccount newAccount = new BankAccount(accNumber, name, deposit);

        accounts.add(newAccount);
        System.out.println("Account created successfully with Account Number: " + accNumber);

        saveAccountsToFile();
    }

    private static int generateAccountNumber() {
        int maxAcc = 1000;
        for (BankAccount acc : accounts) {
            if (acc.getAccNo() > maxAcc) {
                maxAcc = acc.getAccNo();
            }
        }
        return maxAcc + 1;
    }

    private static BankAccount findAccount(int accNumber) {
        for (BankAccount acc : accounts) {
            if (acc.getAccNo() == accNumber) {
                return acc;
            }
        }
        return null;
    }

    private static void depositMoney() {
        System.out.print("Enter Account Number: ");
        int accNumber = sc.nextInt();
        BankAccount account = findAccount(accNumber);

        if (account != null) {
            System.out.print("Enter amount to deposit: ");
            double amount = sc.nextDouble();
            account.deposit(amount);
            saveAccountsToFile();
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void withdrawMoney() {
        System.out.print("Enter Account Number: ");
        int accNumber = sc.nextInt();
        BankAccount account = findAccount(accNumber);

        if (account != null) {
            System.out.print("Enter amount to withdraw: ");
            double amount = sc.nextDouble();
            try {
                account.withdraw(amount);
                saveAccountsToFile();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void checkBalance() {
        System.out.print("Enter Account Number: ");
        int accNumber = sc.nextInt();
        BankAccount account = findAccount(accNumber);

        if (account != null) {
            System.out.println("Current Balance: " + account.getBalance());
        } else {
            System.out.println("Account not found.");
        }
    }
    private static void displayAllAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts available.");
        } else {
            for (BankAccount acc : accounts) {
                acc.displayAccInfo();
            }
        }
    }
    private static void saveAccountsToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (BankAccount acc : accounts) {
                writer.println(acc.toFileString());
            }
        } catch (IOException e) {
            System.out.println("Error saving accounts: " + e.getMessage());
        }
    }

    private static void loadAccountsFromFile() {
        System.out.println("[DEBUG] Loading accounts from: " + new File(FILE_NAME).getAbsolutePath());
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                BankAccount acc = BankAccount.fromFileString(line);
                accounts.add(acc);
            }
        } catch (FileNotFoundException e) {
            System.out.println("[DEBUG] File not found. Starting fresh.");
        } catch (IOException e) {
            System.out.println("Error loading accounts: " + e.getMessage());
        }
    }

}
