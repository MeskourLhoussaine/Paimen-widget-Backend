package ma.m2t.chaabipay.controller;



import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ma.m2t.chaabipay.dtos.*;
import ma.m2t.chaabipay.entites.*;
import ma.m2t.chaabipay.mappers.ImplementMapers;
import ma.m2t.chaabipay.repositories.CreditCardRepository;
import ma.m2t.chaabipay.repositories.PaimentMethodeReposirory;
import ma.m2t.chaabipay.repositories.TokenRepository;
import ma.m2t.chaabipay.repositories.TransactionRepository;
import ma.m2t.chaabipay.services.PaymentMethodService;
import ma.m2t.chaabipay.services.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/Transaction")
@AllArgsConstructor
@NoArgsConstructor
public class TransactionController {
@Autowired
    private TransactionService transactionService;
    @Autowired
    private TokenRepository tokenRepository;
@Autowired
    private CreditCardRepository creditCardRepository;
@Autowired
private PaymentMethodService paymentMethodService;




    @GetMapping("/findAll")
    public List<TransactionDTO> listTransactions() {
        return transactionService.listTransactions();
    }

    /*@PostMapping("/save")
    public TransactionDTO saveTransaction(TransactionDTO transactionDTO) {
        return transactionService.saveTransaction(transactionDTO);
    }*/

    @PutMapping("/update ")
    public TransactionDTO updateTransaction(TransactionDTO transactionDTO) {
        return transactionService.updateTransaction(transactionDTO);
    }

    @DeleteMapping("/delete")
    public void deleteTransaction(TransactionDTO transactionDTO) {
        transactionService.deleteTransaction(transactionDTO);
    }


    @PostMapping("/save")
    public TransactionDTO saveNewTransaction(TransactionDTO transactionDTO) {
        return transactionService.saveNewTransaction(transactionDTO);
    }
//UPDATE


    @PutMapping("/changestatus/{transactionId}/{newstatus}")
    public void updateTransactionStatus(@PathVariable Long transactionId,@PathVariable String newstatus) {
        transactionService.updateTransactionStatus(transactionId,newstatus);
    }

    @GetMapping("/findByMerchantId/{marchandId}")
    public List<TransactionDTO> getAllTransactionsByMerchant(@PathVariable Long marchandId) {
        return transactionService.getAllTransactionsByMerchant(marchandId);
    }

    @GetMapping("/byPaymentMethod")
    public List<TransactionDTO> getAllTransactionsByMethod(@RequestParam Long paymentmethod) {
        return transactionService.getAllTransactionsByMethod(paymentmethod);
    }

    @GetMapping("/{transactionId}")
    public TransactionDTO getTransactionById(@PathVariable Long transactionId) {
        return transactionService.getTransactionById(transactionId);
    }

    @GetMapping("/status/{transactionId}")
    public String getTransactionStatus(@PathVariable Long transactionId) {
        return transactionService.getTransactionStatus(transactionId);
    }
    //ajouter cette fonction pour filtrer
    @GetMapping("/BypymentMethothodName/{name}")
    public List<TransactionDTO> getTransactionsByPaymentMethodName(@PathVariable  String name) {
        return transactionService.getTransactionsByPaymentMethodName(name);
    }
    /* ------Front Marchand------Pour laoder le combobox par les name de pyment methode---*/
    @GetMapping("/pymentMethodeBymerchanId/{merchantId}")
    public List<PaymentMethod> getPaimentMethodeBymerchanId(@PathVariable Long merchantId) {
        return transactionService.getPaimentMethodeBymerchanId(merchantId);
    }
}

