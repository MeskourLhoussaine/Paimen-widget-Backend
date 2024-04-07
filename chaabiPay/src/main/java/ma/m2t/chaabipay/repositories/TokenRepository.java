package ma.m2t.chaabipay.repositories;

import ma.m2t.chaabipay.entites.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token,Long > {
}
