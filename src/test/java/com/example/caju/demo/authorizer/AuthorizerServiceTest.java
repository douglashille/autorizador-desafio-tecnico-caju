package com.example.caju.demo.authorizer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.example.caju.demo.accont.Account;
import com.example.caju.demo.accont.AccountRepository;
import com.example.caju.demo.transaction.TransactionCategoryEnum;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class AuthorizerServiceTest {

    @Autowired
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
    void testTransaction_Approved() {
        AuthorizerTransactionPayloadDTO transaction = AuthorizerTransactionPayloadDTO.builder()
                .account("123")
                .totalAmount(100d)
                .mcc("5811")
                .merchant("PADARIA DO ZE               SAO PAULO BR")
                .build();
        String code = authorizerService.authorizeTransaction(transaction);

        // Assert
        assertEquals(code, "00");
    }

    @Test
    void testDTransaction_Disapproved() {
        AuthorizerTransactionPayloadDTO transaction = AuthorizerTransactionPayloadDTO.builder()
                .account("123")
                .totalAmount(10000d)
                .mcc("5811")
                .merchant("PADARIA DO ZE               SAO PAULO BR")
                .build();
        String code = authorizerService.authorizeTransaction(transaction);

        // Assert
        assertEquals(code, "51");
    }
}
