package nintendods.ei.fti.uantwerpen.bankapplication.ServerSide;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class BankController {
    private Map<String, BankAccount> accounts = new HashMap<>();

    @PostMapping("/createAccount")
    public ResponseEntity<String> createAccount(@RequestParam String name,
                                @RequestParam String bankNumber,
                                @RequestParam int initialBalance) {
        try {
            BankAccount account = new BankAccount(name, bankNumber, initialBalance);
            accounts.put(account.getBankNumber(), account);
            return ResponseEntity.ok("Account created successfully.\nName: " + name + "\nBanknumber: " + bankNumber + "\nInitial balance: " + account.getBalance());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/balance")
    public ResponseEntity<String> getBalance(@RequestParam String bankNumber) {
        BankAccount account = accounts.get(bankNumber);
        if (account != null) {
            return ResponseEntity.ok("Balance of account " + bankNumber + ": " + account.getBalance());
        } else {
            return ResponseEntity.badRequest().body("Invalid bank number: " + bankNumber);
        }
    }

    @PutMapping("/add")
    public ResponseEntity<String> addMoney(@RequestParam String bankNumber, @RequestParam int amount) {
        BankAccount account = accounts.get(bankNumber);
        if (account != null) {
            try {
                account.addMoney(amount);
                return ResponseEntity.ok("New balance: " + account.getBalance());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body("Error: " + e.getMessage());
            }
        } else {
            return ResponseEntity.badRequest().body("Invalid bank number: " + bankNumber);
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeMoney(@RequestParam String bankNumber, @RequestParam int amount) {
        BankAccount account = accounts.get(bankNumber);
        if (account != null) {
            try {
                account.removeMoney(amount);
                return ResponseEntity.ok("New balance: " + account.getBalance());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body("Error: " + e.getMessage());
            }
        } else {
            return ResponseEntity.badRequest().body("Invalid bank number: " + bankNumber);
        }
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transferMoney(@RequestParam String sourceBankNumber,
                                                @RequestParam String destinationBankNumber,
                                                @RequestParam int amount) {
        BankAccount sourceAccount = accounts.get(sourceBankNumber);
        BankAccount destinationAccount = accounts.get(destinationBankNumber);

        if (sourceAccount != null && destinationAccount != null) {
            try {
                sourceAccount.transferTo(destinationAccount, amount);
                return ResponseEntity.ok("Transfer successful.");
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body("Error: " + e.getMessage());
            }
        } else {
            return ResponseEntity.badRequest().body("Invalid bank numbers.");
        }
    }

}
