package nintendods.ei.fti.uantwerpen.bankapplication;

import nintendods.ei.fti.uantwerpen.bankapplication.ServerSide.BankAccount;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class BankAccountTests {

    @Test
    public void testCreateBankAccountWithValidParameters() {
        BankAccount account = new BankAccount("John Doe", "12345678", 100);
        assertEquals("John Doe", account.getName());
        assertEquals("12345678", account.getBankNumber());
        assertEquals(100, account.getBalance());
    }

    @Test
    public void testCreateBankAccountWithNegativeInitialBalance() {
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("John Doe", "12345678", -100));
    }

    @Test
    public void testAddMoneyToBankAccount() {
        BankAccount account = new BankAccount("John Doe", "12345678", 100);
        account.addMoney(50);
        assertEquals(150, account.getBalance());
    }

    @Test
    public void testRemoveMoneyFromBankAccount() {
        BankAccount account = new BankAccount("John Doe", "12345678", 100);
        account.removeMoney(50);
        assertEquals(50, account.getBalance());
    }

    @Test
    public void testTransferMoneyBetweenTwoBankAccounts() {
        BankAccount sourceAccount = new BankAccount("John Doe", "12345678", 100);
        BankAccount destinationAccount = new BankAccount("Jane Smith", "87654321", 50);

        sourceAccount.transferTo(destinationAccount, 50);

        assertEquals(50, sourceAccount.getBalance());
        assertEquals(100, destinationAccount.getBalance());
    }

    @Test
    public void testTransferMoneyWithInsufficientFunds() {
        BankAccount sourceAccount = new BankAccount("John Doe", "12345678", 100);
        BankAccount destinationAccount = new BankAccount("Jane Smith", "87654321", 50);

        assertThrows(IllegalArgumentException.class, () -> sourceAccount.transferTo(destinationAccount, 150));
    }

    @Test
    public void testTransferMoneyWithNegativeAmount() {
        BankAccount sourceAccount = new BankAccount("John Doe", "12345678", 100);
        BankAccount destinationAccount = new BankAccount("Jane Smith", "87654321", 50);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> sourceAccount.transferTo(destinationAccount, -50));
        assertEquals("Amount must be positive", exception.getMessage());
    }

    @Test
    public void testConcurrentOperationsOnJointAccount() throws InterruptedException {
        // Create a joint account
        BankAccount jointAccount = new BankAccount("Joint Account", "123456789", 1000);

        // Create two threads, each representing a family member accessing the joint account
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // Define a shared runnable task for deposit and withdrawal operations
        Runnable task = () -> {
            for (int i = 0; i < 100; i++) {
                try {
                    // Simulate deposit operation
                    jointAccount.addMoney(10);
                    Thread.sleep(10); // Simulate some processing time

                    // Simulate withdrawal operation
                    jointAccount.removeMoney(5);
                    Thread.sleep(10); // Simulate some processing time
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        // Submit the tasks to the executor service
        executorService.submit(task);
        executorService.submit(task);

        // Shutdown the executor service
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        // After concurrent operations, check the final balance
        int expectedBalance = 1000 + (2 * 100 * 10) - (2 * 100 * 5); // Initial balance + (100 deposits - 100 withdrawals)
        assertEquals(expectedBalance, jointAccount.getBalance());
    }
}
