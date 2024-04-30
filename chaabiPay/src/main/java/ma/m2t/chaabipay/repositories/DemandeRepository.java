package ma.m2t.chaabipay.repositories;

import ma.m2t.chaabipay.entites.Demande;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DemandeRepository extends JpaRepository<Demande,Long> {
    List<Demande> findAllByDemandeIsVerifiedFalse();
}