package nintendods.ei.fti.uantwerpen.bankapplication;

import nintendods.ei.fti.uantwerpen.bankapplication.ServerSide.BankAccount;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BankConcurrencyTests {

    @Test
    public void testConcurrentOperationsOnBankAccount() throws InterruptedException {
        BankAccount account = new BankAccount("John Doe", "12345678", 100);

        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            executor.execute(() -> {
                account.addMoney(10);
                account.removeMoney(5);
            });
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
            // Waiting for all threads to finish
        }

        assertEquals(150, account.getBalance());
    }
}
