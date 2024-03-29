package ma.m2t.chaabipay.repositories;

import ma.m2t.chaabipay.entites.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantRepository extends JpaRepository<Merchant,Long> {
}
