package ma.m2t.chaabipay.services.implement;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ma.m2t.chaabipay.entites.Merchant;
import ma.m2t.chaabipay.entites.MerchantMethodePayment;
import ma.m2t.chaabipay.entites.PaymentMethod;
import ma.m2t.chaabipay.exceptions.MerchantExceptionNotFound;
import ma.m2t.chaabipay.repositories.MerchantMethodePaymentRepository;
import ma.m2t.chaabipay.repositories.MerchantRepository;
import ma.m2t.chaabipay.repositories.PaimentMethodeReposirory;
import ma.m2t.chaabipay.services.MerchantMethodePaymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.*;
@Service
@Transactional
@AllArgsConstructor
@NoArgsConstructor
@CrossOrigin("*")
public class MerchantMethodePaymentServiseImpl implements MerchantMethodePaymentService {
    private MerchantRepository merchantRepository;
    private MerchantMethodePaymentRepository merchantMethodePaymentRepository;
    private PaimentMethodeReposirory paimentMethodeReposirory;

    @Override
    public List<Map<String, Object>> getMerchantPaymentMethod(Long merchantId) throws MerchantExceptionNotFound {
        if (!merchantRepository.existsById(merchantId)) {
            throw new MerchantExceptionNotFound("Merchant not found with ID: " + merchantId);
        }

        List<MerchantMethodePayment> existingAssociations = merchantMethodePaymentRepository.findByMerchantMerchantId(merchantId);
        List<Long> existingPaymentMethodIds = new ArrayList<>();
        List<Map<String, Object>> paymentMethods = new ArrayList<>();

        for (MerchantMethodePayment association : existingAssociations) {
            existingPaymentMethodIds.add(association.getPaymentMethod().getPaymentMethodId());
        }

        for (Long paymentMethodId : existingPaymentMethodIds) {
            Optional<PaymentMethod> optionalPaymentMethod = paimentMethodeReposirory.findById(paymentMethodId);
            optionalPaymentMethod.ifPresent(paymentMethod -> {
                Map<String, Object> methodMap = new HashMap<>();
                methodMap.put("paymentMethodId", paymentMethod.getPaymentMethodId());
                methodMap.put("methodName", paymentMethod.getName());
                methodMap.put("methodDescription", paymentMethod.getDescription());
                methodMap.put("methodIconUrl", paymentMethod.getIconUrl());
                paymentMethods.add(methodMap);
            });
        }
        return paymentMethods;
    }


    @Override
    public void selectPaymentMethodInMerchant(Long merchantId, Long paymentMethodId) throws MerchantExceptionNotFound {
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new MerchantExceptionNotFound("Merchant Not found"));

        PaymentMethod paymentMethod = paimentMethodeReposirory.findById(paymentMethodId)
                .orElseThrow(() -> new IllegalArgumentException("Payment Method not found"));

        MerchantMethodePayment existingAssociation = merchantMethodePaymentRepository.findByMerchantMerchantIdAndPaymentMethodPaymentMethodId(merchantId, paymentMethodId);

        if (existingAssociation == null) {
            // If association doesn't exist, create a new one and set isSelected to true
            MerchantMethodePayment newAssociation = new MerchantMethodePayment();
            newAssociation.setMerchant(merchant);
            newAssociation.setPaymentMethod(paymentMethod);
            newAssociation.setStatus(true);
            merchantMethodePaymentRepository.save(newAssociation);
        } else {
            // If association exists, toggle isSelected value
            existingAssociation.setStatus(!existingAssociation.isStatus());
            merchantMethodePaymentRepository.save(existingAssociation);
        }

        //
        System.out.println("Payment method " + paymentMethod.getName() + " for merchant " + merchant.getMerchantName() + " is now " + (existingAssociation != null && existingAssociation.isStatus() ? "selected" : "deselected"));
    }
}