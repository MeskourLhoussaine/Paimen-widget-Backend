package ma.m2t.chaabipay.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ma.m2t.chaabipay.dtos.MerchantDTO;
import ma.m2t.chaabipay.exceptions.MerchantExceptionNotFound;
import ma.m2t.chaabipay.repositories.MerchantRepository;
import ma.m2t.chaabipay.services.MerchantMethodePaymentService;
import ma.m2t.chaabipay.services.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    @Autowired
    private MerchantRepository merchantRepository;
    /****************----< find All  Merchant Api >----**************/
                                   /**--#-- */
    @GetMapping("/findAll")
    public List<MerchantDTO> listMerchantes() {
        return merchantService.listMerchantes();
    }

    /******************----< Add Merchant Api avaec la generate de secret key  >----***********/
                                         /**--#-- */
    @PostMapping("/save")
    public MerchantDTO saveMerchant(@RequestBody MerchantDTO MerchantDTO) {
        return merchantService.saveMerchant(MerchantDTO);
    }

               /******************----< update Merchant Api >----*********************/
                         /**--#-- */


               @PreAuthorize("hasRole('ROLE_MARCHAND')")
                         @PutMapping("/update")
                         public MerchantDTO updateCustomer(@RequestBody MerchantDTO marchandDTO){
                             return merchantService.updateMerchant(marchandDTO);
                         }
    /*****************----< delete Merchant Api >----************************/
                                 /**--#-- */
    @DeleteMapping("/delete")
    public void deleteMerchant(MerchantDTO merchantDTO) {
        merchantService.deleteMerchant(merchantDTO);

    }

    //DELETE
    @DeleteMapping("/delete/{id}")
    public void DeleteByID(@PathVariable(name = "id") Long id){
        merchantService.deleteMerchantById(id);
    }

    /*********----< Add Merchant Api fait la meme chose que  saveMerchant  >----*******/
                                 /**--#-- */
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
                merchantDTO.getMerchantUrl(),   // Ajout du logo du marchand
                merchantDTO.getCallback(),       // Ajout du callback
                merchantDTO.getServiceid(),
                merchantDTO.getAccessKey()// Ajout de l'ID du service
        );
    }

    /**--< doner au marchand des methods avec le status True par default (en peut modifier a false par default )  >-*****/
                                 /**--#-- */

    @PostMapping("/{merchantId}/affecterPaymentMethods")
    public void associerMethodesPaiementToMerchant(@PathVariable Long merchantId, @RequestBody Set<Long> methodePaiementIds) throws MerchantExceptionNotFound {
        merchantService.associerMethodesPaiementToMerchant(merchantId, methodePaiementIds);
    }

    /*****************----< Verifier les droit d'acces >----************************/
                                  /**--#-- */

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
    /*****************----< find Merchant by Id >----************************/
                                    /**--#-- */
    @GetMapping("/findById/{id}")
    public MerchantDTO getMerchantById(@PathVariable(name = "id") Long merchantId) throws MerchantExceptionNotFound {
        return merchantService.findMerchantById(merchantId);
    }
    /*****************----< delete Merchant by Id >----************************/
                             /**--#-- */
    public void deleteMerchantById(@PathVariable(name = "id") Long merchantId) {
        merchantService.deleteMerchantById(merchantId);
    }
/*
    public List<MerchantDTO> getAllMerchantsByMethod(Long methodId) {
        return merchantService.getAllMerchantsByMethod(methodId);
    }*/
    /*****************----< GET All methods using by merchant Id >----************************/
                                    /**--#-- */
    @GetMapping("/methods/{marchandId}")
    public List<Map<String, Object>> getMarchandPaymentMethod(@PathVariable Long marchandId) throws MerchantExceptionNotFound {
        return merchantMethodePaymentService.getMerchantPaymentMethod(marchandId);
    }

    /*usin for authenticated by marchand */
    @GetMapping("/merchandId/{merchantName}")
    public int findMesrchantIdbyMerchantName(@PathVariable String merchantName) {
        return merchantService.findMesrchantIdbyMerchantName(merchantName);
    }
}