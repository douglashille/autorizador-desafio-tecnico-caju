package com.example.caju.demo.merchant;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MerchantService {

    @Autowired
    MerchantRepository repository;

    public List<Merchant> createDefaults() {
        if (repository.findAll().size() > 0)
            return null;
            
        return repository.saveAll(Merchant.defaultValues());
    }

}
