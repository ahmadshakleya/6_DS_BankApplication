package nintendods.ei.fti.uantwerpen.bankapplication;

import nintendods.ei.fti.uantwerpen.bankapplication.ServerSide.BankAccount;
import nintendods.ei.fti.uantwerpen.bankapplication.ServerSide.BankServerApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = BankServerApplication.class) // Specify your main application class
@AutoConfigureMockMvc
public class BankControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateAccountWithValidParameters() throws Exception {
        mockMvc.perform(post("/createAccount")
                        .param("name", "John Doe")
                        .param("number", "12345678")
                        .param("initialBalance", "100"))
                .andExpect(status().isOk())
                .andExpect(content().string("Account created successfully."));
    }

}
