package com.example.caju.demo.merchant;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
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
@Entity
@Table(indexes = {
        @Index(name = "idx_merchant", columnList = "merchant"),
        @Index(name = "idx_merchant_mcc", columnList = "mcc"),
})
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String merchant;
    private String mcc;

    public static List<Merchant> defaultValues() {
        List<Merchant> merchants = new ArrayList<>();
        merchants.add(Merchant.builder().merchant("UBER TRIP                   SAO PAULO BR").mcc("5411").build());
        merchants.add(Merchant.builder().merchant("UBER EATS                   SAO PAULO BR").mcc("5412").build());
        merchants.add(Merchant.builder().merchant("PAG*JoseDaSilva          RIO DE JANEI BR").mcc("5811").build());
        merchants.add(Merchant.builder().merchant("PICPAY*BILHETEUNICO           GOIANIA BR").mcc("5812").build());
        merchants.add(Merchant.builder().merchant("UBER ENVIO                  JOINVILLE BR").mcc("6011").build());

        return merchants;
    }

}
