package ma.m2t.chaabipay.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ma.m2t.chaabipay.dtos.MerchantDTO;
import ma.m2t.chaabipay.services.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
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

@PostMapping("/{marchandId}/associer-methodes-paiement")
public ResponseEntity<?> associerMethodesPaiement(@PathVariable Long marchandId, @RequestBody Set<Long> methodePaiementIds) {
    merchantService.associerMethodePaiement(marchandId, methodePaiementIds);
    return ResponseEntity.ok().build();
}
}
//controller