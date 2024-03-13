package nintendods.ei.fti.uantwerpen.bankapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
public class BankServerApplication {

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication.run(BankServerApplication.class, args);
        InetAddress ip = InetAddress.getLocalHost();
        System.out.println("Server IP address: " + ip.getHostAddress());
    }

    @RestController
    public static class BankController {

        private final AtomicInteger balance = new AtomicInteger(0);
        private final Object lock = new Object(); // Lock for synchronization

        @GetMapping("/balance")
        public int getBalance() {
            synchronized (lock) {
                return balance.get();
            }
        }

        @PostMapping("/add")
        public int addMoney(@RequestBody int amount) {
            synchronized (lock) {
                balance.addAndGet(amount);
                return balance.get();
            }
        }

        @PostMapping("/remove")
        public int removeMoney(@RequestBody int amount) {
            synchronized (lock) {
                balance.addAndGet(-amount);
                return balance.get();
            }
        }

        @PostMapping("/get")
        public int getMoney(@RequestBody int amount) {
            synchronized (lock) {
                if (balance.get() >= amount) {
                    balance.addAndGet(-amount);
                    return amount;
                } else {
                    return 0;
                }
            }
        }
    }

}