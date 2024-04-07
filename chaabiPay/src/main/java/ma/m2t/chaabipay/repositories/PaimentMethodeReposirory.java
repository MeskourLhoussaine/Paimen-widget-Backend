package ma.m2t.chaabipay.repositories;

import ma.m2t.chaabipay.entites.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PaimentMethodeReposirory extends JpaRepository<PaymentMethod,Long> {


}
