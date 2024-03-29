package ma.m2t.chaabipay.controller;

import ma.m2t.chaabipay.dtos.TransactionDTO;
import ma.m2t.chaabipay.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/Transaction")
public class TransactionController {
    @Autowired
    private TransactionService  transactionService;
    @GetMapping("/fincAll")
    public List<TransactionDTO> listTransactions() {
        return transactionService.listTransactions();
    }
@PostMapping("/save")
    public TransactionDTO saveTransaction(TransactionDTO transactionDTO) {
        return transactionService.saveTransaction(transactionDTO);
    }
@PutMapping("/update ")
    public TransactionDTO updateTransaction(TransactionDTO transactionDTO) {
        return transactionService.updateTransaction(transactionDTO);
    }
@DeleteMapping("/delete")
    public void deleteTransaction(TransactionDTO transactionDTO) {
        transactionService.deleteTransaction(transactionDTO);
    }
}
