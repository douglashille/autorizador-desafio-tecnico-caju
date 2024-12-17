package com.example.caju.demo.merchant;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantRepository extends JpaRepository<Merchant, UUID> {

    Merchant findFirstByMerchant(String merchant);

    Merchant findFirstByMcc(String mcc);

}
