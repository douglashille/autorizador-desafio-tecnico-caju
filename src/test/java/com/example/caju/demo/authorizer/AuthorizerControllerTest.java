package com.example.caju.demo.authorizer;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import com.example.caju.demo.accont.Account;
import com.example.caju.demo.accont.AccountRepository;
import com.example.caju.demo.transaction.TransactionCategoryEnum;

// @WebMvcTest(AuthorizerControllerTest.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class AuthorizerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthorizerService authorizerService;

    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        Account account = Account.builder()
                .account("123")
                .category(TransactionCategoryEnum.FOOD.name())
                .value(100d)
                .build();
        accountRepository.save(account);
    }

    @Test
    void testAuthorize_Success() throws Exception {
        // payload de entrada
        String requestBody = "{" +
                "\"account\": \"123\"," +
                "\"totalAmount\": 100.00," +
                "\"mcc\": \"5811\"," +
                "\"merchant\": \"PADARIA DO ZE               SAO PAULO BR\"" +
                "}";

        String code = "00";

        Mockito.when(authorizerService.authorizeTransaction(any(AuthorizerTransactionPayloadDTO.class)))
                .thenReturn(code);

        // Act e Assert
        mockMvc.perform(post("/authorize")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("00"));
    }

    @Test
    void testAuthorize_Failure() throws Exception {
        String code = "51";
        Mockito.when(authorizerService.authorizeTransaction(any(AuthorizerTransactionPayloadDTO.class)))
                .thenReturn(code);

        // payload de entrada
        String requestBody = "{" +
                "\"account\": \"123\"," +
                "\"totalAmount\": 1000.00," +
                "\"mcc\": \"5811\"," +
                "\"merchant\": \"PADARIA DO ZE               SAO PAULO BR\"" +
                "}";

        // Act e Assert
        mockMvc.perform(post("/authorize")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("51"))
                .andDo(print());
    }

}
