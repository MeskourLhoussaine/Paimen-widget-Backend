package ma.m2t.chaabipay.services.implement;

import ma.m2t.chaabipay.entites.implement.Transaction;
import ma.m2t.chaabipay.repository.implemente.TransactionRepository;
import ma.m2t.chaabipay.services.GenericServices;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TransactionService extends GenericServices<Transaction> {
public TransactionService(TransactionRepository transactionRepository){
    super(transactionRepository);
}
}
