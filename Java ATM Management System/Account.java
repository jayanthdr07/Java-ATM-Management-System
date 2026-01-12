import java.util.*;

class Account {
    private int accNo;
    private String name;
    private long phone;
    private int pin;
    private double balance;

    public Account(int accNo, String name, long phone, int pin, double balance) {
        this.accNo = accNo;
        this.name = name;
        this.phone = phone;
        this.pin = pin;
        this.balance = balance;
    }

    public int getAccNo() {
        return accNo;
    }

    public long getPhone() {
        return phone;
    }

    public boolean validatePin(int enteredPin) {
        return pin == enteredPin;
    }

    public void changePin(int newPin) {
        pin = newPin;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amt) {
        balance += amt;
    }

    public boolean withdraw(double amt) {
        if (amt <= balance) {
            balance -= amt;
            return true;
        }
        return false;
    }

    public void showDetails() {
        System.out.println("NAME  : " + name);
        System.out.println("PHONE : " + phone);
    }
}

class ATM {
    private HashMap<Integer, Account> accounts = new HashMap<>();
    private Scanner sc = new Scanner(System.in);
    private Random rand = new Random();

    public ATM() {
        // Multiple accounts
        accounts.put(1001, new Account(1001, "JAYANTH", 8436382982L, 1010, 10000));
        accounts.put(1002, new Account(1002, "ARJUN", 9876543210L, 2020, 15000));
        accounts.put(1003, new Account(1003, "RAHUL", 9123456780L, 3030, 20000));
    }

    public void start() {
        while (true) {
            System.out.println("\n=================================================");
            System.out.println("           WELCOME TO INDIAN BANK ATM");
            System.out.println("=================================================");
            System.out.println("1. CARD OPERATION");
            System.out.println("2. CARDLESS OPERATION");
            System.out.println("0. EXIT");
            System.out.print("ENTER CHOICE: ");

            int ch = sc.nextInt();
            if (ch == 1)
                cardOperation();
            else if (ch == 2)
                cardlessOperation();
            else
                break;
        }
        System.out.println("THANK YOU FOR USING INDIAN BANK ATM");
    }

    private Account getAccount() {
        System.out.print("ENTER ACCOUNT NUMBER: ");
        int accNo = sc.nextInt();
        return accounts.get(accNo);
    }

    private void cardOperation() {
        Account acc = getAccount();
        if (acc == null) {
            System.out.println("INVALID ACCOUNT NUMBER");
            return;
        }

        System.out.print("ENTER PIN: ");
        int pin = sc.nextInt();
        if (!acc.validatePin(pin)) {
            System.out.println("INCORRECT PIN");
            return;
        }

        while (true) {
            System.out.println("\n1. BALANCE ENQUIRY");
            System.out.println("2. WITHDRAW");
            System.out.println("3. DEPOSIT");
            System.out.println("4. PIN CHANGE");
            System.out.println("0. BACK");
            System.out.print("ENTER CHOICE: ");

            int ch = sc.nextInt();
            if (ch == 0) break;

            switch (ch) {
                case 1:
                    System.out.println("AVAILABLE BALANCE = " + acc.getBalance());
                    break;

                case 2:
                    System.out.print("ENTER AMOUNT TO WITHDRAW: ");
                    double w = sc.nextDouble();
                    System.out.println(acc.withdraw(w)
                            ? "WITHDRAW SUCCESSFUL"
                            : "INSUFFICIENT FUNDS");
                    break;

                case 3:
                    System.out.print("ENTER AMOUNT TO DEPOSIT: ");
                    acc.deposit(sc.nextDouble());
                    System.out.println("DEPOSIT SUCCESSFUL");
                    break;

                case 4:
                    otpVerification(acc);
                    System.out.print("ENTER NEW PIN: ");
                    acc.changePin(sc.nextInt());
                    System.out.println("PIN CHANGED SUCCESSFULLY");
                    break;
            }
        }
    }

    private void cardlessOperation() {
        Account acc = getAccount();
        if (acc == null) {
            System.out.println("INVALID ACCOUNT NUMBER");
            return;
        }

        acc.showDetails();
        otpVerification(acc);

        System.out.println("1. DEPOSIT");
        System.out.println("2. WITHDRAW");
        System.out.print("ENTER CHOICE: ");
        int ch = sc.nextInt();

        System.out.print("ENTER AMOUNT: ");
        double amt = sc.nextDouble();

        if (ch == 1) {
            acc.deposit(amt);
            System.out.println("DEPOSIT SUCCESSFUL");
        } else if (ch == 2) {
            System.out.println(acc.withdraw(amt)
                    ? "WITHDRAW SUCCESSFUL"
                    : "INSUFFICIENT FUNDS");
        }

        System.out.println("AVAILABLE BALANCE = " + acc.getBalance());
    }

    private void otpVerification(Account acc) {
        int otp = 100000 + rand.nextInt(900000);
        System.out.println("OTP SENT TO REGISTERED MOBILE: " + otp);

        System.out.print("ENTER OTP: ");
        int userOtp = sc.nextInt();

        if (otp != userOtp) {
            System.out.println("INVALID OTP - TRANSACTION CANCELLED");
            System.exit(0);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.start();
    }
}
