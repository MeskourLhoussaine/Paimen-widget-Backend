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




    @GetMapping("/fincAll")
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

    /*#################create Transaction ################"*/
/*
    @PostMapping("/save")
    public ResponseEntity<TransactionDTO> saveTransaction(@RequestBody TransactionDTO transactionDTO) {
        // Assurez-vous que paymentMethodType et merchantId ne sont pas nuls
        if (transactionDTO == null || transactionDTO.getPaymentMethodType() == null || transactionDTO.getMerchantId() == null) {
            return ResponseEntity.badRequest().build();
        }

        // Enregistrez la transaction en utilisant votre service TransactionService
        TransactionDTO savedTransaction = transactionService.saveTransaction(transactionDTO);

        // Renvoyez une réponse contenant la transaction enregistrée
        return ResponseEntity.ok(savedTransaction);
    }

*/
    @PostMapping("/save")
    public TransactionDTO saveNewTransaction(TransactionDTO transactionDTO) {
        return transactionService.saveNewTransaction(transactionDTO);
    }


}

