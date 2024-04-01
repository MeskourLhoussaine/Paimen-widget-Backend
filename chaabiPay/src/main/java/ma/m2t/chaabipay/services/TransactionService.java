package ma.m2t.chaabipay.services;

import ma.m2t.chaabipay.dtos.MerchantDTO;
import ma.m2t.chaabipay.dtos.TransactionDTO;

import java.util.List;

public interface TransactionService {
    List<TransactionDTO> listTransactions();
    TransactionDTO saveTransaction(TransactionDTO transactionDTO);

    TransactionDTO updateTransaction(TransactionDTO transactionDTO);
    void deleteTransaction(TransactionDTO transactionDTO);
    //fonctin hashage
    String calculateHmac(String merchantId, String orderId, double amount, String currency, String secretKey);
}
