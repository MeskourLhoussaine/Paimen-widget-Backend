package ma.m2t.chaabipay.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ma.m2t.chaabipay.dtos.MerchantDTO;
import ma.m2t.chaabipay.services.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/merchants")

public class MerchantController {
    @Autowired
    private MerchantService merchantService;

    @GetMapping("/findAll")
    public List<MerchantDTO> listMerchantes() {
        return merchantService.listMerchantes();
    }
@PostMapping("/save")
    public MerchantDTO saveMerchant( @RequestBody MerchantDTO MerchantDTO) {
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



    @PostMapping("/generate-secret-key")
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
                merchantDTO.getServiceid()       // Ajout de l'ID du service
        );
    }

    @PostMapping("/associer-methodes-paiement")
    public ResponseEntity<String> associerMethodesPaiement(@RequestParam("marchandId") Long marchandId,
                                                           @RequestBody Set<Long> methodePaiementIds) {
        try {
            merchantService.associerMethodesPaiement(marchandId, methodePaiementIds);
            return ResponseEntity.ok("Associations des méthodes de paiement effectuées avec succès.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Une erreur est survenue lors de l'association des méthodes de paiement : " + e.getMessage());
        }
    }
/**#########################Verifier les droit d'acces####################################*/

@GetMapping("/atorise")
    public boolean checkAccessRights(String merchantHost, String sucretkey, String merchantName, String requestHMAC) {
        return merchantService.checkAccessRights(merchantHost, sucretkey, merchantName, requestHMAC);
    }
}

