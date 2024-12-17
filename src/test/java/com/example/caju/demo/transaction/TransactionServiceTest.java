package com.example.caju.demo.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.example.caju.demo.accont.Account;
import com.example.caju.demo.accont.AccountRepository;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class TransactionServiceTest {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountRepository accountRepository;

    private Account account;

    @BeforeEach
    void setUp() {
        account = Account.builder()
                .account("123")
                .category(TransactionCategoryEnum.FOOD.name())
                .value(1000d)
                .build();
        accountRepository.save(account);
    }

    @Test
    void testDebitValue_Success() {
        boolean result = transactionService.debitValue("123", TransactionCategoryEnum.FOOD.name(), 200d);

        // Assert
        assertTrue(result);

        // Verifica o saldo atualizado no banco
        Account accountRecord = accountRepository.findById(account.getId()).orElse(null);
        assertNotNull(accountRecord);
        assertEquals(800d, accountRecord.getValue());
    }

    @Test
    void testDebitValue_InsufficientBalance() {
        boolean result = transactionService.debitValue("123", TransactionCategoryEnum.FOOD.name(), 1500d);

        assertFalse(result);

        // Verifica que o saldo permanece o mesmo
        Account accountRecord = accountRepository.findById(account.getId()).orElse(null);
        assertNotNull(accountRecord);
        assertEquals(1000d, accountRecord.getValue());
    }

}
