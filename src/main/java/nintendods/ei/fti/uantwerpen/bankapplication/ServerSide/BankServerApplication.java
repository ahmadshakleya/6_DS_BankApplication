package nintendods.ei.fti.uantwerpen.bankapplication.ServerSide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
public class BankServerApplication {

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication.run(BankServerApplication.class, args);
        InetAddress ip = InetAddress.getLocalHost();
        System.out.println("Please connect to server IP address: " + ip.getHostAddress() + " at port 8080");
    }
}
