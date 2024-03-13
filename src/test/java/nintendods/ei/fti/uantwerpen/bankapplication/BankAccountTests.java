package nintendods.ei.fti.uantwerpen.bankapplication;

import nintendods.ei.fti.uantwerpen.bankapplication.ServerSide.BankAccount;
import org.junit.jupiter.api.Test;

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
}
