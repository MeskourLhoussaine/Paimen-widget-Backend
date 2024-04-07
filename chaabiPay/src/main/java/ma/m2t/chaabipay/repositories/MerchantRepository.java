package ma.m2t.chaabipay.repositories;

import ma.m2t.chaabipay.entites.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MerchantRepository extends JpaRepository<Merchant,Long> {
    Optional<Merchant> findByMerchantHost(String merchantHost);


}
