package ma.m2t.chaabipay.services.implement;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import ma.m2t.chaabipay.dtos.DemandeDTO;
import ma.m2t.chaabipay.entites.Demande;
import ma.m2t.chaabipay.exceptions.MerchantExceptionNotFound;
import ma.m2t.chaabipay.mappers.ImplementMapers;
import ma.m2t.chaabipay.repositories.DemandeRepository;
import ma.m2t.chaabipay.services.DemandeService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@CrossOrigin("*")
public class DemandeServiceImpl implements DemandeService {
    private final DemandeRepository demandeRepository;
    private final ImplementMapers payMapper;


    @Override
    public DemandeDTO saveNewDemande(DemandeDTO demandeDTO) {
        demandeDTO.setDemandeIsAccepted(false);
        demandeDTO.setDemandeIsVerified(false);
        Demande demande = payMapper.fromDemandeDTO(demandeDTO);
        Demande savedDemande = demandeRepository.save(demande);
        return payMapper.fromDemande(savedDemande);
    }

    @Override
    public List<DemandeDTO> getAllDemandes() {
        List<Demande> demandes = demandeRepository.findAll();
        return demandes.stream().map(payMapper::fromDemande).collect(Collectors.toList());
    }

    @Override
    public DemandeDTO getDemande(Long demandeId) {
        Optional<Demande> demandeOptional = demandeRepository.findById(demandeId);
        Demande demande = demandeOptional.orElseThrow(() -> new EntityNotFoundException("Demande not found with id: " + demandeId));
        return payMapper.fromDemande(demande);
    }

    @Override
    public List<DemandeDTO> getAllDemandesNotVerified() {
        List<Demande> demandes = demandeRepository.findAllByDemandeIsVerifiedFalse();
        return demandes.stream().map(payMapper::fromDemande).collect(Collectors.toList());
    }

    @Override
    public DemandeDTO UpdateDemandeRejected(Long demandeId) throws MerchantExceptionNotFound {
        Demande demande = demandeRepository.findById(demandeId)
                .orElseThrow(() -> new MerchantExceptionNotFound ("Demande not found with ID: " + demandeId));
        demande.setDemandeIsVerified(true);
        demande.setDemandeIsAccepted(false);
        Demande savedDemande = demandeRepository.save(demande);
        return payMapper.fromDemande(savedDemande);
    }

    @Override
    public DemandeDTO UpdateDemandeAccepted(Long demandeId) throws MerchantExceptionNotFound {
        Demande demande = demandeRepository.findById(demandeId)
                .orElseThrow(() -> new MerchantExceptionNotFound ("Demande not found with ID: " + demandeId));
        demande.setDemandeIsVerified(true);
        demande.setDemandeIsAccepted(true);
        Demande savedDemande = demandeRepository.save(demande);
        // Implement logic to save merchand here
        return payMapper.fromDemande(savedDemande);
    }
}