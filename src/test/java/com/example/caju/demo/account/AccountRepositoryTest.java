package com.example.caju.demo.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.caju.demo.accont.Account;
import com.example.caju.demo.accont.AccountRepository;
import com.example.caju.demo.transaction.TransactionCategoryEnum;

@DataJpaTest
@ActiveProfiles("test")
public class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    void testFindFirstByAccountAndCategoryWithLock_RetunrsAccount() {
        Account account = Account.builder()
                .account("123")
                .category(TransactionCategoryEnum.FOOD.name())
                .value(100d)
                .build();
        accountRepository.save(account);

        Account result = accountRepository.findFirstByAccountAndCategoryWithLock("123",
                TransactionCategoryEnum.FOOD.name()).orElse(null);

        assertNotNull(result);
        assertEquals("123", result.getAccount());
    }

    @Test
    void testFindFirstByAccountAndCategoryWithLock_RetunrsNull() {
        Account result = accountRepository.findFirstByAccountAndCategoryWithLock("999",
                TransactionCategoryEnum.FOOD.name()).orElse(null);

        assertNull(result);
    }

}
