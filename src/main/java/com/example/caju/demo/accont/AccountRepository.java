package com.example.caju.demo.accont;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import jakarta.persistence.LockModeType;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    // L4. Questão aberta - transações simultaneas,
    // Lock no registro para processar uma alteração/transação por vez
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT account FROM Account account WHERE account.account = :account AND account.category = :category")
    Optional<Account> findFirstByAccountAndCategoryWithLock(String account, String category);

}
