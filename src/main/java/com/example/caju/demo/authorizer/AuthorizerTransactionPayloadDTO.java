package com.example.caju.demo.authorizer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizerTransactionPayloadDTO {
    private String account;
    private Double totalAmount;
    private String mcc;
    private String merchant;
}
