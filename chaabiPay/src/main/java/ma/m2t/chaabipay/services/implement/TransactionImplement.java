package ma.m2t.chaabipay.services.implement;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.m2t.chaabipay.dtos.CreditCardDTO;
import ma.m2t.chaabipay.dtos.TokenDTO;
import ma.m2t.chaabipay.dtos.TransactionDTO;
import ma.m2t.chaabipay.entites.*;
import ma.m2t.chaabipay.mappers.ImplementMapers;
import ma.m2t.chaabipay.repositories.MerchantRepository;
import ma.m2t.chaabipay.repositories.PaimentMethodeReposirory;
import ma.m2t.chaabipay.repositories.TransactionRepository;
import ma.m2t.chaabipay.services.PaymentMethodService;
import ma.m2t.chaabipay.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
@Transactional
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class TransactionImplement implements TransactionService {

        private ImplementMapers dtoMapper;
        private TransactionRepository transactionRepository;
        private PaimentMethodeReposirory paymentMethodRepository;
         private ImplementMapers implementMapers;
          private MerchantRepository merchantRepository;




        @Override
        public List<TransactionDTO> listTransactions() {
                List<Transaction> transactions = transactionRepository.findAll();
                List<TransactionDTO> transactionDTOs = transactions.stream()
                        .map(transaction -> dtoMapper.fromTransaction((Transaction) transactions))
                        .collect(Collectors.toList());
    /*
    List<MerchantDTO> merchantDTOS = new ArrayList<>();
    for (Merchant merchant : merchants) {
        MerchantDTO merchantDTO = dtoMapper.fromMerchant(merchant);
        merchantDTOS.add(merchantDTO);
    }
    */
                return transactionDTOs;
        }

    @Override
    public TransactionDTO saveNewTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = dtoMapper.fromtransactionDTO(transactionDTO);

        // Set PaymentMethod and Merchant if IDs are available
        if (transactionDTO.getPaymentMethodId() != null) {
            PaymentMethod paymentMethod = paymentMethodRepository.findById(transactionDTO.getPaymentMethodId()).orElse(null);
            transaction.setPaymentMethod(paymentMethod);
        }
        if (transactionDTO.getMerchantId() != null) {
            Merchant merchant = merchantRepository.findById(transactionDTO.getMerchantId()).orElse(null);
            transaction.setMerchant(merchant);
        }

        Transaction savedTransaction = transactionRepository.save(transaction);
        return dtoMapper.fromTransaction(savedTransaction);
    }

        @Override
        public TransactionDTO updateTransaction(TransactionDTO transactionDTO) {
                if (transactionDTO == null) {
                        log.error("TransactionDTO is null. Cannot update null object.");
                        throw new IllegalArgumentException("TransactionDTO cannot be null");
                }
                log.info("Updating Transaction");
                Transaction merchant = dtoMapper.fromtransactionDTO(transactionDTO);
                // Utilisation de Optional pour gérer le cas où le marchand n'est pas trouvé
                Optional<Transaction> updatedTransaction = transactionRepository.findById(merchant.getTransactionId());
                if (updatedTransaction.isPresent()) {
                        updatedTransaction = Optional.ofNullable(transactionRepository.save(merchant));
                }
                return updatedTransaction.map(dtoMapper::fromTransaction)
                        .orElseThrow(() -> new RuntimeException("Transaction not found"));
        }

        @Override
        public void deleteTransaction(TransactionDTO transactionDTO) {
                if (transactionDTO == null) {
                        log.error("MerchantDTO is null. Cannot delete null object.");
                        throw new IllegalArgumentException("MerchantDTO cannot be null");
                }
                log.info("Deleting Merchant");
                Transaction transaction = dtoMapper.fromtransactionDTO(transactionDTO);
                // Utilisation d'une lambda pour une suppression plus concise
                transactionRepository.delete(transaction);
        }


}