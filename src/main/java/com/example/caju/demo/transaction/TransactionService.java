package com.example.caju.demo.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.example.caju.demo.accont.Account;
import com.example.caju.demo.accont.AccountRepository;

@Service
public class TransactionService {

    @Autowired
    AccountRepository accountRepository;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean debitValue(String account, String category, double value) {
        Account accountRecord = accountRepository.findFirstByAccountAndCategoryWithLock(account, category).orElse(null);

        // se não tem conta+categoria
        if (accountRecord == null)
            return false;

        double accountValue = accountRecord.getValue();

        if (accountValue >= value) {
            accountRecord.setValue(accountValue - value);
            accountRepository.save(accountRecord);
            return true;
        }

        return false;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean creditValue(String account, String category, double value) {
        Account accountRecord = accountRepository.findFirstByAccountAndCategoryWithLock(account, category).orElse(null);

        // se não tem conta+categoria
        if (accountRecord == null)
            return false;

        double accountValue = accountRecord.getValue();
        accountRecord.setValue(accountValue + value);

        accountRepository.save(accountRecord);
        return true;

    }
}
