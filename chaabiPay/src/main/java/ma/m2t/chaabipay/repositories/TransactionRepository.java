package ma.m2t.chaabipay.repositories;

import ma.m2t.chaabipay.entites.Merchant;
import ma.m2t.chaabipay.entites.PaymentMethod;
import ma.m2t.chaabipay.entites.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findAllByMerchant(Optional<Merchant> merchant);

    List<Transaction> findAllByPaymentMethod(Optional<PaymentMethod> paymentMethod);


}
