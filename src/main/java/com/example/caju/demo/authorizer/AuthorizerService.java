package com.example.caju.demo.authorizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.caju.demo.accont.AccountRepository;
import com.example.caju.demo.merchant.Merchant;
import com.example.caju.demo.merchant.MerchantRepository;
import com.example.caju.demo.transaction.TransactionCategoryEnum;
import com.example.caju.demo.transaction.TransactionService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthorizerService {

    @Autowired
    MerchantRepository merchantRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TransactionService transactionService;

    /**
     * Autoriza transação
     * 
     * @param transactionData dados da transação a ser autorizada
     * @return código para retornar no response
     */
    public String authorizeTransaction(AuthorizerTransactionPayloadDTO transactionData) {
        String code = "";

        try {

            String mccCategory = resolveCategory(transactionData.getMerchant(), transactionData.getMcc());

            if (transactionService.debitValue(transactionData.getAccount(),
                    mccCategory,
                    transactionData.getTotalAmount())) {

                code = AuthorizerCodeEnum.APPROVED.getCode();

            } else if (transactionService.debitValue(transactionData.getAccount(),
                    TransactionCategoryEnum.CASH.name(),
                    transactionData.getTotalAmount())) {

                // L2. Autorizador com fallback - debita da categoria cash
                code = AuthorizerCodeEnum.APPROVED.getCode();

            } else {
                code = AuthorizerCodeEnum.DISAPPROVED.getCode();
            }

        } catch (Exception e) {
            log.error("erro autorizacao",e);
            code = AuthorizerCodeEnum.FATAL.getCode();
        }

        return code;
    }

    /**
     * Retorna categoria da transação baseado no comerciante e no código MCC
     * 
     * @param merchant nome do comerciante
     * @param mcc      código do mcc enviado na transação
     * @return categoria a ser usada na transação
     */
    private String resolveCategory(String merchant, String mcc) {
        String mccResolved = resolveMCC(merchant, mcc);
        String mccCategory = getMCCCategory(mccResolved);

        return mccCategory;
    }

    /**
     * L3.Dependente do comerciante
     * nome do comerciante sempre deve ter maior precedência sobre as MCC
     * 
     * @param merchant nome comerciante
     * @param mcc      mcc enviado no payload da transação
     * @return mcc vinculado ao comerciante, se não tiver o mcc da transação
     */
    private String resolveMCC(String merchant, String mcc) {
        Merchant dataRepository = merchantRepository.findFirstByMerchant(merchant);
        if (dataRepository == null) {
            return mcc;
        }

        return dataRepository.getMcc();
    }

    /**
     * Categoria do MCC
     *
     * @param mcc código de 4 dígitos MCC.
     * @return Categoria do MCC.
     */
    private String getMCCCategory(String mcc) {
        switch (mcc) {
            case "5411":
            case "5412":
                return TransactionCategoryEnum.FOOD.name();
            case "5811":
            case "5812":
                return TransactionCategoryEnum.MEAL.name();
            default:
                return TransactionCategoryEnum.CASH.name();
        }
    }

}
