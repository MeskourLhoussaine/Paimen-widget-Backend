package ma.m2t.chaabipay.services.implement;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.m2t.chaabipay.dtos.TransactionDTO;
import ma.m2t.chaabipay.entites.Merchant;
import ma.m2t.chaabipay.entites.PaymentMethod;
import ma.m2t.chaabipay.entites.Transaction;
import ma.m2t.chaabipay.mappers.ImplementMapers;
import ma.m2t.chaabipay.repositories.MerchantRepository;
import ma.m2t.chaabipay.repositories.PaimentMethodeReposirory;
import ma.m2t.chaabipay.repositories.TransactionRepository;
import ma.m2t.chaabipay.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class TransactionImplement implements TransactionService {

    @Autowired
    private ImplementMapers dtoMapper;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private PaimentMethodeReposirory paymentMethodRepository;

    @Autowired
    private ImplementMapers implementMapers;

    @Autowired
    private MerchantRepository merchantRepository;

    @Override
    public List<TransactionDTO> listTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        List<TransactionDTO> transactionDTOs = transactions.stream()
                .map(dtoMapper::fromTransaction)
                .collect(Collectors.toList());
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

    @Override
    public void updateTransactionStatus(Long transactionId, String newStatus) {

        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found"));

        // Validate newStatus
        if (!isValidStatus(newStatus)) {
            throw new IllegalArgumentException("Invalid status: " + newStatus);
        }

        // Check if the current status is "completed" or "Cancelled"
        if (transaction.getStatus().equalsIgnoreCase("completed")) {
            throw new IllegalStateException("Transaction is already completed and cannot be modified.");
        } else if (transaction.getStatus().equalsIgnoreCase("Cancelled")) {
            if (newStatus.equalsIgnoreCase("completed")) {
                throw new IllegalStateException("Transaction is Cancelled and cannot be completed.");
            }
            throw new IllegalStateException("Transaction is already Cancelled and cannot be modified.");
        }

        // Update the status of the transaction
        transaction.setStatus(newStatus);

        // Save the updated transaction
        transactionRepository.save(transaction);

    }

    @Override
    public List<TransactionDTO> getAllTransactionsByMerchant(Long merchantId) {
        Optional<Merchant> merchant = merchantRepository.findById(merchantId);
        List<Transaction> transactions = transactionRepository.findAllByMerchant(merchant);
        List<TransactionDTO> transactionDTO=new ArrayList<>();
        for (Transaction t:transactions){
            transactionDTO.add(dtoMapper.fromTransaction(t));
        }
        return transactionDTO;
    }





    @Override
    public List<TransactionDTO> getAllTransactionsByMethod(Long paymentMethodId) {
        Optional<PaymentMethod> paymentMethod = paymentMethodRepository.findById(paymentMethodId);
        List<Transaction> transactions = transactionRepository.findAllByPaymentMethod(paymentMethod);
        List<TransactionDTO> transactionDTO=new ArrayList<>();
        for (Transaction t:transactions){
            transactionDTO.add(dtoMapper.fromTransaction(t));
        }
        return transactionDTO;
    }

                           //#####Front Marchand####### Pour filter par name methode//
    @Override
    public List<TransactionDTO> getTransactionsByPaymentMethodName(String name) {
        // Retrieve the PaymentMethod entity by name
        Optional<PaymentMethod> paymentMethodOptional = paymentMethodRepository.findByName(name);

        // Check if the payment method exists
        if (paymentMethodOptional.isPresent()) {
            PaymentMethod paymentMethod = paymentMethodOptional.get();

            // Retrieve transactions associated with the payment method
            List<Transaction> transactions = transactionRepository.findAllByPaymentMethod(Optional.of(paymentMethod));

            // Map transactions to TransactionDTO objects
            return transactions.stream()
                    .map(dtoMapper::fromTransaction)
                    .collect(Collectors.toList());
        } else {
            // Throw an exception if the payment method is not found
            throw new EntityNotFoundException("Payment method not found with name: " + name);
        }
    }


    @Override
    public TransactionDTO getTransactionById(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found"));

        TransactionDTO transactionDTO = dtoMapper.fromTransaction(transaction);

        return transactionDTO;
    }

    @Override
    public String getTransactionStatus(Long transactionID) {
        Transaction transaction = transactionRepository.findById(transactionID)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found"));

        return transaction.getStatus();
    }

    private boolean isValidStatus(String status) {
        String[] validStatusValues = {"completed", "pending", "Cancelled"};

        for (String validStatus : validStatusValues) {
            if (validStatus.equalsIgnoreCase(status)) {
                return true;
            }
        }
        return false;
    }
    /* ------Front Marchand------Pour laoder le combobox par les name de pyment methode---*/

/*---- pour filtrer la transaction par merchantId est retourner les methode payment-----*/
    @Override
    public List<PaymentMethod> getPaimentMethodeBymerchanId(Long merchantId) {
        // Filter transactions by merchantId and retrieve distinct payment methods
        List<PaymentMethod> paymentMethods = transactionRepository.findAll()
                .stream()
                .filter(transaction -> transaction.getMerchant().getMerchantId().equals(merchantId))
                .map(Transaction::getPaymentMethod)
                .distinct()
                .collect(Collectors.toList());

        if (!paymentMethods.isEmpty()) {
            return paymentMethods;
        } else {
            // Handle case when no payment methods are found for the merchant
            log.error("No payment methods found for merchant with id {}", merchantId);
            throw new IllegalArgumentException("No payment methods found for merchant");
        }
    }
}
