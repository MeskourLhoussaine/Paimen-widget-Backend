package ma.m2t.chaabipay.repositories;

import ma.m2t.chaabipay.entites.MerchantMethodePayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MerchantMethodePaymentRepository extends JpaRepository<MerchantMethodePayment,Long> {

    List<MerchantMethodePayment> findByMerchantMerchantId(Long merchantId);
    MerchantMethodePayment findByMerchantMerchantIdAndPaymentMethodPaymentMethodId(Long merchantId, Long paymentMethodId);
}
