package nintendods.ei.fti.uantwerpen.bankapplication.ServerSide;

public class BankAccount {
    private String name;
    private String bankNumber;
    private int balance;

    public BankAccount(String name, String bankNumber, int initialBalance) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
        if (bankNumber == null || bankNumber.isEmpty()) {
            throw new IllegalArgumentException("Bank number cannot be blank");
        }
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance must be positive");
        }
        this.name = name;
        this.bankNumber = bankNumber;
        this.balance = initialBalance;
    }

    public String getName() {
        return name;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public synchronized int getBalance() {
        return balance;
    }

    public synchronized void addMoney(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        balance += amount;
    }

    public synchronized void removeMoney(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (amount > balance) {
            throw new IllegalArgumentException("Not enough funds");
        }
        balance -= amount;
    }

    public synchronized void transferTo(BankAccount destination, int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (amount > balance) {
            throw new IllegalArgumentException("Not enough funds");
        }

        balance -= amount;
        destination.addMoney(amount);
    }
}
