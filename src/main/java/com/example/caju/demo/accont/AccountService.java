package com.example.caju.demo.accont;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    AccountRepository repository;

    public void createDefaults() {
        if (repository.findAll().size() > 0)
            return;

        repository.saveAll(Account.defaultValues());
    }
}
