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
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private MerchantRepository merchantRepository;
    @Autowired
    private MerchantMethodePaymentRepository merchantMethodePaymentRepository;
    @Autowired
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

///*******************************************************************************


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
        System.out.println("Payment method " + paymentMethod.getName() + " for merchant " + merchant.getMerchantName() + " is now " + (existingAssociation != null && existingAssociation.isStatus() ? "selected" : "deselected"));
    }


    /**********----< get status de la methode affectue au merchant >----*******/
                               /**--#-- */
    @Override
    public boolean findStatusMerchantPaymentByMerchantIdAndPaymentMethodId(Long merchantId, Long paymentMethodId) {
        MerchantMethodePayment existingAssociation = merchantMethodePaymentRepository.findByMerchantMerchantIdAndPaymentMethodPaymentMethodId(merchantId, paymentMethodId);

        if (existingAssociation != null) {
            // Si une association existe pour le marchand et la méthode de paiement donnés,
            // retourne l'état de l'association
            return existingAssociation.isStatus();
        }

        return false; // Si aucune association n'est trouvée, retourne false
    }

           /**********----------< update status >---------- ****************/
    @Override
    public void updateMerchantMethodStatusByPaymentMethodId(Long paymentMethodId, Long merchantId, boolean status) throws MerchantExceptionNotFound {
        // Récupérer le mode de paiement correspondant à l'ID donné
        PaymentMethod paymentMethod = paimentMethodeReposirory.findById(paymentMethodId)
                .orElseThrow(() -> new MerchantExceptionNotFound("Payment Method not found with ID: " + paymentMethodId));

        // Récupérer le marchand correspondant à l'ID donné
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new MerchantExceptionNotFound("Merchant not found with ID: " + merchantId));

        // Récupérer la liste des relations MerchantMethodePayment associées à ce mode de paiement et à ce marchand
        List<MerchantMethodePayment> merchantMethodPayments = merchantMethodePaymentRepository.findByPaymentMethodAndMerchant(paymentMethod, merchant);

        // Mettre à jour le statut des marchands associés au mode de paiement
        for (MerchantMethodePayment merchantMethodPayment : merchantMethodPayments) {
            merchantMethodPayment.setStatus(status);
            // Enregistrer les modifications dans la base de données
            merchantMethodePaymentRepository.save(merchantMethodPayment);
        }
    }




}
