package com.example.caju.demo.merchant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
public class MerchantRepositoryTest {

    @Autowired
    private MerchantRepository merchantRepository;

    @Test
    void testFindFirstByMcc_ReturnsMerchant() {
        Merchant merchant = Merchant.builder()
                .mcc("1234")
                .merchant("Test Merchant")
                .build();
        merchantRepository.save(merchant);

        Merchant result = merchantRepository.findFirstByMcc("1234");

        assertNotNull(result);
        assertEquals("1234", result.getMcc());
    }

    @Test
    void testFindFirstByMcc_ReturnsNull() {
        // MCC que não existe
        Merchant result = merchantRepository.findFirstByMcc("5678");

        assertNull(result);
    }

    @Test

    void testfindFirstByMerchant_ReturnsMerchant() {
        Merchant merchant = Merchant.builder()
                .mcc("1234")
                .merchant("Test Merchant")
                .build();
        merchantRepository.save(merchant);

        Merchant result = merchantRepository.findFirstByMerchant("Test Merchant");

        assertNotNull(result);
        assertEquals("Test Merchant", result.getMerchant());
    }

    @Test
    void testfindFirstByMerchant_ReturnsNull() {
        Merchant result = merchantRepository.findFirstByMerchant("Ninguem");
        assertNull(result);
    }
}
