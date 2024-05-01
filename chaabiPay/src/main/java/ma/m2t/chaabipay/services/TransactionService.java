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
    void updateTransactionStatus(Long transactionId , String newStatus);
    List<TransactionDTO> getAllTransactionsByMerchant(Long merchant);
    List<TransactionDTO> getAllTransactionsByMethod(Long methodId);
    //Ajouter la fonction  meskour #############
    List<TransactionDTO> getTransactionsByPaymentMethodName(String  name);
    //#############################

    TransactionDTO getTransactionById(Long transactionId);
    String getTransactionStatus(Long transactionID);
    //fonctin hashag
//#############frontmarchand##############"
    List<PaymentMethod> getPaimentMethodeBymerchanId(Long merchantId);

    //30/04
    // Méthode pour récupérer le nombre de transactions effectuées par un client chez un marchand donné
    int getNumberOfTransactionsByClientAndMerchant(String clientName, Long merchantId);

}