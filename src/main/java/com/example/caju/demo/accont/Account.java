package com.example.caju.demo.accont;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.caju.demo.transaction.TransactionCategoryEnum;

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
        @Index(name = "idx_account_category", columnList = "account,category"),
})
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String account;
    private String category;

    private Double value;

    public static List<Account> defaultValues() {
        List<Account> accounts = new ArrayList<>();
        accounts.add(Account.builder().account("123").category(TransactionCategoryEnum.MEAL.name()).value(Double.valueOf(100)).build());
        accounts.add(Account.builder().account("123").category(TransactionCategoryEnum.CASH.name()).value(Double.valueOf(100)).build());
        accounts.add(Account.builder().account("456").category(TransactionCategoryEnum.FOOD.name()).value(Double.valueOf(100)).build());
        accounts.add(Account.builder().account("456").category(TransactionCategoryEnum.CASH.name()).value(Double.valueOf(100)).build());        

        return accounts;
    }
}
