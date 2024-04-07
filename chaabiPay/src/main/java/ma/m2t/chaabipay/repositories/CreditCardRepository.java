package ma.m2t.chaabipay.repositories;

import ma.m2t.chaabipay.entites.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
    CreditCard findByNumberCard(String numberCard);

}
