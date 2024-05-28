package ma.m2t.chaabipay.controller;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import ma.m2t.chaabipay.dtos.DemandeDTO;
import ma.m2t.chaabipay.dtos.MerchantDTO;
import ma.m2t.chaabipay.emailing.EmailService;
import ma.m2t.chaabipay.entites.Demande;
import ma.m2t.chaabipay.enums.Status;
import ma.m2t.chaabipay.exceptions.MerchantExceptionNotFound;
import ma.m2t.chaabipay.repositories.DemandeRepository;
import ma.m2t.chaabipay.services.DemandeService;
import ma.m2t.chaabipay.services.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("/api/demandes")
public class DemandeApi {
    @Autowired
    private final DemandeService demandeService;
    @Autowired
    private DemandeRepository demandeRepository;
    @Autowired
    private MerchantService marchandService;
    @Autowired
    private EmailService emailService;

    @PostMapping("/save")
    @Transactional
    public ResponseEntity<DemandeDTO> saveNewDemande(@RequestBody DemandeDTO demandeDTO) throws MessagingException {
        DemandeDTO savedDemande = demandeService.saveNewDemande(demandeDTO);
        this.emailService.sendDemandeVerificationEmail(demandeDTO.getDemandeMarchandEmail());
        return new ResponseEntity<>(savedDemande, HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<List<DemandeDTO>> getAllDemandes() {
        List<DemandeDTO> demandes = demandeService.getAllDemandes();
        return new ResponseEntity<>(demandes, HttpStatus.OK);
    }

    @GetMapping("/demande/{demandeId}")
    public ResponseEntity<DemandeDTO> getDemande(@PathVariable Long demandeId) {
        DemandeDTO demandeDto = demandeService.getDemande(demandeId);
        return new ResponseEntity<>(demandeDto, HttpStatus.OK);
    }

    @GetMapping("/not-verified")
    public ResponseEntity<List<DemandeDTO>> getAllDemandesNotVerified() {
        List<DemandeDTO> demandes = demandeService.getAllDemandesNotVerified();
        return new ResponseEntity<>(demandes, HttpStatus.OK);
    }

    @PutMapping("/{demandeId}/rejected")
    @Transactional
    public ResponseEntity<DemandeDTO> updateDemandeRejected(@PathVariable Long demandeId) throws MerchantExceptionNotFound, MessagingException {
        DemandeDTO updatedDemande = demandeService.UpdateDemandeRejected(demandeId);

        Optional<Demande> demandeOptional = demandeRepository.findById(demandeId);

        //email
        if (demandeOptional.isPresent()) {
            Demande demande = demandeOptional.get();
            this.emailService.sendValidationRejectedEmail(demande.getDemandeMarchandEmail());
        } else {
            throw new MerchantExceptionNotFound("Demande with ID " + demandeId + " not found");
        }

        return new ResponseEntity<>(updatedDemande, HttpStatus.OK);
    }

    @PutMapping("/{demandeId}/accepted")
    @Transactional
    public ResponseEntity<DemandeDTO> updateDemandeAccepted(@PathVariable Long demandeId) throws MerchantExceptionNotFound, MessagingException {
        //
        DemandeDTO updatedDemande = demandeService.UpdateDemandeAccepted(demandeId);


        //
        MerchantDTO marchandDTO = new MerchantDTO();
        marchandDTO.setMarchandEmail(updatedDemande.getDemandeMarchandEmail());
        marchandDTO.setMerchantName(updatedDemande.getDemandeMarchandName());
        marchandDTO.setMerchantDescrip(updatedDemande.getDemandeMarchandDescription());
        marchandDTO.setMarchandPhone(updatedDemande.getDemandeMarchandPhone());
        marchandDTO.setMerchantHost(updatedDemande.getDemandeMarchandHost());
        marchandDTO.setMerchantUrl(updatedDemande.getDemandeMarchandLogoUrl());
        marchandDTO.setMarchandTypeActivite(updatedDemande.getDemandeMarchandTypeActivite());
        marchandDTO.setMarchandRcIf(updatedDemande.getDemandeMarchandRcIf());
        marchandDTO.setMarchandSiegeAddresse(updatedDemande.getDemandeMarchandSiegeAddresse());
        marchandDTO.setMarchandDgName(updatedDemande.getDemandeMarchandDgName());
        marchandDTO.setMarchandFormejuridique(updatedDemande.getDemandeMarchandFormejuridique());
        marchandDTO.setMarchandAnneeActivite(updatedDemande.getDemandeMarchandAnneeActivite());
        marchandDTO.setMarchandStatus(Status.JustCreated);

        //email
        this.emailService.sendValidationAcceptedEmail(marchandDTO.getMarchandEmail());

        // Save the new MarchandDTO
        marchandService.saveMerchant(marchandDTO);

        return new ResponseEntity<>(updatedDemande, HttpStatus.OK);
    }


    @PutMapping("/{demandeId}/update/accepted")
    @Transactional
    public ResponseEntity<DemandeDTO> updateDemande(@PathVariable Long demandeId, @RequestBody DemandeDTO demandeDTO) throws MerchantExceptionNotFound, MessagingException {
        //
        DemandeDTO updatedDemande = demandeService.UpdateDemandeValuesAndAccepted(demandeId,demandeDTO);

        //email
        this.emailService.sendValidationAcceptedEmail(demandeDTO.getDemandeMarchandEmail());

        //
        MerchantDTO marchandDTO = new MerchantDTO();
        marchandDTO.setMarchandEmail(updatedDemande.getDemandeMarchandEmail());
        marchandDTO.setMerchantName(updatedDemande.getDemandeMarchandName());
        marchandDTO.setMerchantDescrip(updatedDemande.getDemandeMarchandDescription());
        marchandDTO.setMarchandPhone(updatedDemande.getDemandeMarchandPhone());
        marchandDTO.setMerchantHost(updatedDemande.getDemandeMarchandHost());
        marchandDTO.setMerchantUrl(updatedDemande.getDemandeMarchandLogoUrl());
        marchandDTO.setMarchandTypeActivite(updatedDemande.getDemandeMarchandTypeActivite());
        marchandDTO.setMarchandRcIf(updatedDemande.getDemandeMarchandRcIf());
        marchandDTO.setMarchandSiegeAddresse(updatedDemande.getDemandeMarchandSiegeAddresse());
        marchandDTO.setMarchandDgName(updatedDemande.getDemandeMarchandDgName());
        marchandDTO.setMarchandFormejuridique(updatedDemande.getDemandeMarchandFormejuridique());
        marchandDTO.setMarchandAnneeActivite(updatedDemande.getDemandeMarchandAnneeActivite());
        marchandDTO.setMarchandStatus(Status.JustCreated);

        // Save the new MarchandDTO
        marchandService.saveMerchant(marchandDTO);

        return new ResponseEntity<>(updatedDemande, HttpStatus.OK);
    }


    @PutMapping("/{demandeId}/update")
    public ResponseEntity<DemandeDTO> updateDemandeValues(@PathVariable Long demandeId, @RequestBody DemandeDTO demandeDTO) throws MerchantExceptionNotFound {
        //
        DemandeDTO updatedDemande = demandeService.UpdateDemandeValues(demandeId,demandeDTO);

        return new ResponseEntity<>(updatedDemande, HttpStatus.OK);
    }



///****************************************************************************************************
    /// SSE
/*
    @GetMapping("/not-verified-sse")
    public Flux<DemandeDTO> getAllDemandesNotVerifiedSEE() {
        return demandeService.getAllDemandesNotVerifiedSEE();
    }*/

}