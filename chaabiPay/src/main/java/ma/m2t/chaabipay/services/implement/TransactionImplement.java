package ma.m2t.chaabipay.services.implement;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.m2t.chaabipay.dtos.MerchantDTO;
import ma.m2t.chaabipay.dtos.TransactionDTO;
import ma.m2t.chaabipay.entites.Merchant;
import ma.m2t.chaabipay.entites.Transaction;
import ma.m2t.chaabipay.mappers.ImplementMapers;
import ma.m2t.chaabipay.repositories.MerchantRepository;
import ma.m2t.chaabipay.repositories.TransactionRepository;
import ma.m2t.chaabipay.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class TransactionImplement implements TransactionService {
        private ImplementMapers dtoMapper;
        @Autowired
        private TransactionRepository transactionRepository;





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
        public TransactionDTO saveTransaction(TransactionDTO transactionDTO) {
                if (transactionDTO == null) {
                        log.error("TransactionDTO is null. Cannot save null object.");
                        throw new IllegalArgumentException("TransactionDTO cannot be null");
                }
                log.info("Saving new Merchant");
                Transaction transaction = dtoMapper.fromtransactionDTO(transactionDTO);
                // Utilisation de Optional pour éviter les NullPointerException
                Optional<Transaction> savedTransaction = Optional.ofNullable(transactionRepository.save(transaction));
                return savedTransaction.map(dtoMapper::fromTransaction)
                        .orElseThrow(() -> new RuntimeException("Error while saving transaction"));
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
                Optional<Transaction> updatedTransaction = transactionRepository.findById(merchant.getId());
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
        /***####################### fonction de hachage ####################################*/
        @Override
        public String calculateHmac(String merchantId, String orderId, double amount, String currency, String secretKey) {
                String data = merchantId + ':' + orderId + ':' + amount + ':' + currency;
                return hmacDigest(data, secretKey, "HmacSHA1");
        }

        private String hmacDigest(String data, String key, String algorithm) {
                try {
                        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), algorithm);
                        Mac mac = Mac.getInstance(algorithm);
                        mac.init(secretKeySpec);
                        byte[] bytes = mac.doFinal(data.getBytes());
                        StringBuilder result = new StringBuilder();
                        for (byte b : bytes) {
                                result.append(String.format("%02x", b));
                        }
                        return result.toString();
                } catch (NoSuchAlgorithmException | InvalidKeyException e) {
                        log.error("Error occurred while generating HMAC: {}", e.getMessage());
                        throw new RuntimeException("Error occurred while generating HMAC", e);
                }
        }
}
