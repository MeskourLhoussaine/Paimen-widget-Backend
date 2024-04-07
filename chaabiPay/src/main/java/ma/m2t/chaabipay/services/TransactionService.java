package ma.m2t.chaabipay.services;

import ma.m2t.chaabipay.dtos.MerchantDTO;
import ma.m2t.chaabipay.dtos.TransactionDTO;
import ma.m2t.chaabipay.entites.Merchant;
import ma.m2t.chaabipay.entites.PaymentMethod;
import ma.m2t.chaabipay.entites.Transaction;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TransactionService {
    List<TransactionDTO> listTransactions();
    TransactionDTO saveNewTransaction(TransactionDTO transactionDTO);

    TransactionDTO updateTransaction(TransactionDTO transactionDTO);
    void deleteTransaction(TransactionDTO transactionDTO);
    //fonctin hashage



}