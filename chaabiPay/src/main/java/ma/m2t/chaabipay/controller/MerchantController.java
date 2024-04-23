package ma.m2t.chaabipay.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ma.m2t.chaabipay.dtos.MerchantDTO;
import ma.m2t.chaabipay.exceptions.MerchantExceptionNotFound;
import ma.m2t.chaabipay.services.MerchantMethodePaymentService;
import ma.m2t.chaabipay.services.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/merchants")
@AllArgsConstructor
@NoArgsConstructor
public class MerchantController {
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private MerchantMethodePaymentService merchantMethodePaymentService;

    @GetMapping("/findAll")
    public List<MerchantDTO> listMerchantes() {
        return merchantService.listMerchantes();
    }

    @PostMapping("/save")
    public MerchantDTO saveMerchant(@RequestBody MerchantDTO MerchantDTO) {
        return merchantService.saveMerchant(MerchantDTO);
    }

    @PutMapping("/update")
    public MerchantDTO updateMerchant(MerchantDTO merchantDTO) {
        return merchantService.updateMerchant(merchantDTO);
    }

    @DeleteMapping("/delete")
    public void deleteMerchant(MerchantDTO merchantDTO) {
        merchantService.deleteMerchant(merchantDTO);
    }


    @PostMapping("/saveAndGenerate-secret-key")
    public String generateAndSaveSecretKey(@RequestBody MerchantDTO merchantDTO) {
        // Vérifier si les informations requises (nom et hôte du marchand) sont fournies
        if (merchantDTO.getMerchantName() == null || merchantDTO.getMerchantHost() == null) {
            throw new IllegalArgumentException("Merchant name and host are required");
        }

        // Appeler le service pour générer et enregistrer la clé secrète
        return merchantService.generateAndSaveSecretKey(
                merchantDTO.getMerchantName(),
                merchantDTO.getMerchantHost(),
                merchantDTO.getMerchantDescrip(), // Ajout de la description du marchand
                merchantDTO.getMerchantLogo(),   // Ajout du logo du marchand
                merchantDTO.getCallback(),       // Ajout du callback
                merchantDTO.getServiceid(),
                merchantDTO.getAccessKey()// Ajout de l'ID du service
        );
    }

   /* @PostMapping("/associer-methodes-paiement")
    public ResponseEntity<String> associerMethodesPaiementToMerchant(@RequestParam("marchandId") Long marchandId,
                                                           @RequestBody Set<Long> methodePaiementIds) {
        try {
            merchantService.associerMethodesPaiementToMerchant(marchandId, methodePaiementIds);
            return ResponseEntity.ok("Associations des méthodes de paiement effectuées avec succès.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Une erreur est survenue lors de l'association des méthodes de paiement : " + e.getMessage());
        }
    }*/

    @PostMapping("/{merchantId}/affecterPaymentMethods")
    public void associerMethodesPaiementToMerchant(@PathVariable Long merchantId, @RequestBody Set<Long> methodePaiementIds) throws MerchantExceptionNotFound {
        merchantService.associerMethodesPaiementToMerchant(merchantId, methodePaiementIds);
    }

    /**
     * #########################Verifier les droit d'acces####################################
     */


    @GetMapping("/permission")
    public Boolean testPermission(
            @RequestParam String hostname,
            @RequestParam String accessKey,
            @RequestParam String merchantId,
            @RequestParam String orderId,
            @RequestParam double amount,
            @RequestParam String currency,
            @RequestParam String hmac) throws Exception {

        Boolean hasPermission = merchantService.checkAccessRights(hostname, accessKey, merchantId, orderId, amount, currency, hmac);

        System.out.println("Hostname: " + hostname);
        System.out.println("Secret Key: " + accessKey);
        System.out.println("Merchant ID: " + merchantId);
        System.out.println("Order ID: " + orderId);
        System.out.println("Amount: " + amount);
        System.out.println("Currency: " + currency);
        System.out.println("HMAC: " + hmac);
        System.out.println("Has Permission: " + hasPermission);

        return hasPermission;
    }

    @GetMapping("/{id}")
    public MerchantDTO getMerchantById(@PathVariable(name = "id") Long merchantId) throws MerchantExceptionNotFound {
        return merchantService.getMerchantById(merchantId);
    }

    public void deleteMerchantById(@PathVariable(name = "id") Long merchantId) {
        merchantService.deleteMerchantById(merchantId);
    }
/*
    public List<MerchantDTO> getAllMerchantsByMethod(Long methodId) {
        return merchantService.getAllMerchantsByMethod(methodId);
    }*/

    @GetMapping("/methods/{marchandId}")
    public List<Map<String, Object>> getMarchandPaymentMethod(@PathVariable Long marchandId) throws MerchantExceptionNotFound {
        return merchantMethodePaymentService.getMerchantPaymentMethod(marchandId);
    }

}