package ma.m2t.chaabipay.repositories;

import ma.m2t.chaabipay.entites.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}
