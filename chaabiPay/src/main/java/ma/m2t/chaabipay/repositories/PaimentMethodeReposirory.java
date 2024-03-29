package ma.m2t.chaabipay.repositories;

import ma.m2t.chaabipay.entites.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaimentMethodeReposirory extends JpaRepository<PaymentMethod,Long> {
}
