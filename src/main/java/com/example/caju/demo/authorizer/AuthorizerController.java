package com.example.caju.demo.authorizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Autorizador", description = "Autorizador de Transações")
@Slf4j
@RestController
@RequestMapping("/")
public class AuthorizerController {

    @Autowired
    AuthorizerService authorizerService;

    @PostMapping("/authorize")
    public AuthorizerResponseDTO authorize(@RequestBody AuthorizerTransactionPayloadDTO transactionData) {
        AuthorizerResponseDTO response = new AuthorizerResponseDTO();
        String code = authorizerService.authorizeTransaction(transactionData);
        response.setCode(code);
        return response;
    }
}
