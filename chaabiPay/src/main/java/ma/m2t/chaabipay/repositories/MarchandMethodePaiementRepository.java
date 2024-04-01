package ma.m2t.chaabipay.repositories;

import ma.m2t.chaabipay.entites.MarchandMethodePaiement;
import ma.m2t.chaabipay.entites.Merchant;
import ma.m2t.chaabipay.entites.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MarchandMethodePaiementRepository extends JpaRepository<MarchandMethodePaiement,Long> {
    List<MarchandMethodePaiement> findByMarchandId(Long marchandId);
}
