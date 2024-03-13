package nintendods.ei.fti.uantwerpen.bankapplication.ServerSide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

@SpringBootApplication
public class BankServerApplication {

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication.run(BankServerApplication.class, args);
        //SpringApplication.run(BankClientApplication.class, args);
        InetAddress ip = InetAddress.getLocalHost();
        System.out.println("Server IP address: " + ip.getHostAddress());
    }
}
