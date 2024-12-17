package com.example.caju.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.caju.demo.accont.AccountService;
import com.example.caju.demo.merchant.MerchantService;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class InitConfigApp {

    @Autowired
    MerchantService merchantService;
    @Autowired
    AccountService accountService;

    /**
     * Cria registros após iniciar para testes
     * 
     */
    @PostConstruct
    public void inicializaDados() {
        // cria dados dos comerciantes
        merchantService.createDefaults();
        // criar constas+categorias iniciais com saldos
        accountService.createDefaults();
    }
}
