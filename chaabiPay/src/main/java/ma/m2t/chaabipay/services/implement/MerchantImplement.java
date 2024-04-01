package ma.m2t.chaabipay.services.implement;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.m2t.chaabipay.dtos.MerchantDTO;
import ma.m2t.chaabipay.entites.MarchandMethodePaiement;
import ma.m2t.chaabipay.entites.Merchant;
import ma.m2t.chaabipay.entites.PaymentMethod;
import ma.m2t.chaabipay.mappers.ImplementMapers;
import ma.m2t.chaabipay.repositories.MarchandMethodePaiementRepository;
import ma.m2t.chaabipay.repositories.MerchantRepository;
import ma.m2t.chaabipay.repositories.PaimentMethodeReposirory;
import ma.m2t.chaabipay.services.MerchantService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class MerchantImplement implements MerchantService {

    private ImplementMapers dtoMapper;
    private MerchantRepository merchantRepository;
    private PaimentMethodeReposirory paimentMethodeReposirory;
    private MarchandMethodePaiementRepository marchandMethodePaiementRepository;


    @Override
    public List<MerchantDTO> listMerchantes() {
        List<Merchant> merchants = merchantRepository.findAll();
        List<MerchantDTO> merchantDTOS = merchants.stream()
                .map(merchant -> dtoMapper.fromMerchant(merchant))
                .collect(Collectors.toList());
    /*
    List<MerchantDTO> merchantDTOS = new ArrayList<>();
    for (Merchant merchant : merchants) {
        MerchantDTO merchantDTO = dtoMapper.fromMerchant(merchant);
        merchantDTOS.add(merchantDTO);
    }
    */
        return merchantDTOS;
    }


    @Override
    public MerchantDTO saveMerchant(MerchantDTO merchantDTO) {
        if (merchantDTO == null) {
            log.error("MerchantDTO is null. Cannot save null object.");
            throw new IllegalArgumentException("MerchantDTO cannot be null");
        }
        log.info("Saving new Merchant");
        Merchant merchant = dtoMapper.fromMerchantDTO(merchantDTO);
        // Utilisation de Optional pour éviter les NullPointerException
        Optional<Merchant> savedMerchant = Optional.ofNullable(merchantRepository.save(merchant));
        return savedMerchant.map(dtoMapper::fromMerchant)
                .orElseThrow(() -> new RuntimeException("Error while saving merchant"));
    }

    @Override
    public MerchantDTO updateMerchant(MerchantDTO merchantDTO) {
        if (merchantDTO == null) {
            log.error("MerchantDTO is null. Cannot update null object.");
            throw new IllegalArgumentException("MerchantDTO cannot be null");
        }
        log.info("Updating Merchant");
        Merchant merchant = dtoMapper.fromMerchantDTO(merchantDTO);
        // Utilisation de Optional pour gérer le cas où le marchand n'est pas trouvé
        Optional<Merchant> updatedMerchant = merchantRepository.findById(merchant.getId());
        if (updatedMerchant.isPresent()) {
            updatedMerchant = Optional.ofNullable(merchantRepository.save(merchant));
        }
        return updatedMerchant.map(dtoMapper::fromMerchant)
                .orElseThrow(() -> new RuntimeException("Merchant not found"));
    }

    @Override
    public void deleteMerchant(MerchantDTO merchantDTO) {
        if (merchantDTO == null) {
            log.error("MerchantDTO is null. Cannot delete null object.");
            throw new IllegalArgumentException("MerchantDTO cannot be null");
        }
        log.info("Deleting Merchant");
        Merchant merchant = dtoMapper.fromMerchantDTO(merchantDTO);
        // Utilisation d'une lambda pour une suppression plus concise
        merchantRepository.delete(merchant);
    }

    /******************************************************************************
     * ********************************************************************/

    @Override
    public String generateAndSaveSecretKey(String merchantName, String merchantHost, String merchantDescrip, String merchantLogo, String callback, String serviceid) {
        // Générer une clé secrète aléatoire
        String secretKey = generateSecretKey();

        // Créer un nouveau marchand avec toutes les informations fournies
        Merchant merchant = new Merchant();
        merchant.setMerchantName(merchantName);
        merchant.setMerchantHost(merchantHost);
        merchant.setMerchantDescrip(merchantDescrip);
        merchant.setMerchantLogo(merchantLogo);
        merchant.setCallback(callback);
        merchant.setServiceid(serviceid);
        merchant.setSucretkey(secretKey); // Attention à la faute de frappe dans le nom du setter

        // Enregistrer le marchand dans la base de données
        merchantRepository.save(merchant);

        // Retourner la clé secrète générée
        return secretKey;
    }
    /******************************************************************************
     * ********************************************************************/


@Override
public void associerMethodesPaiement(Long marchandId, Set<Long> methodePaiementIds) throws Exception {
    // Récupérer le marchand
    Merchant merchant = merchantRepository.findById(marchandId)
            .orElseThrow(() -> new Exception("Marchand non trouvé"));

    // Récupérer les associations existantes pour ce marchand
    List<MarchandMethodePaiement> associationsExistants = marchandMethodePaiementRepository.findByMarchandId(marchandId);
    Set<Long> idsMethodePaiementExistants = associationsExistants.stream()
            .map(association -> association.getMethodePaiement().getId())
            .collect(Collectors.toSet());

    // Parcourir les IDs des méthodes de paiement choisis
    for (Long methodePaiementId : methodePaiementIds) {
        if (!idsMethodePaiementExistants.contains(methodePaiementId)) {
            // Récupérer la méthode de paiement depuis la base de données
            PaymentMethod methodePaiement = paimentMethodeReposirory.findById(methodePaiementId)
                    .orElseThrow(() -> new RuntimeException("Méthode de paiement non trouvée"));

            // Créer une nouvelle association marchand-méthode de paiement
            MarchandMethodePaiement association = new MarchandMethodePaiement();
            association.setMarchand(merchant);
            association.setMethodePaiement(methodePaiement);
            association.setEtat(false); // état par défaut
            marchandMethodePaiementRepository.save(association);
        } else {
            // Mettre à jour l'association existante si elle existe déjà
            for (MarchandMethodePaiement associationExistante : associationsExistants) {
                if (associationExistante.getMethodePaiement().getId().equals(methodePaiementId)) {
                    associationExistante.setEtat(true); // Mettre à jour l'état à false
                    marchandMethodePaiementRepository.save(associationExistante);
                    break;
                }
            }
        }
    }
}

    /****************Méthode pour générer une clé secrète aléatoire**************************
     * ********************************************************************/

    // Méthode pour générer une clé secrète aléatoire
    private String generateSecretKey() {
        // Définir la longueur de la clé (en bytes)
        int keyLength = 32; // Par exemple, une clé de 256 bits

        // Créer un objet SecureRandom pour générer des nombres aléatoires sécurisés
        SecureRandom secureRandom = new SecureRandom();

        // Générer un tableau de bytes aléatoires
        byte[] randomBytes = new byte[keyLength];
        secureRandom.nextBytes(randomBytes);

        // Convertir les bytes en une chaîne Base64 pour une représentation lisible
        return Base64.getEncoder().encodeToString(randomBytes);
    }

    //la fonction de hashage
    @Override
    public boolean checkAccessRights(String merchantHost, String sucretkey, String merchantName, String requestHMAC) {
        try {
            Optional<Merchant> optionalMerchant = merchantRepository.findByMerchantHost(merchantHost);
            if (optionalMerchant.isPresent()) {
                Merchant merchant = optionalMerchant.get();

                if (merchant.getMerchantName().equals(merchantName) && merchant.getSucretkey().equals(sucretkey)) {
                    String generatedHMAC = generateHMAC(merchantHost + merchantName, sucretkey);
System.out.println(generatedHMAC);
                    return generatedHMAC.equals(requestHMAC);
                }
            }
        } catch (Exception e) {
            log.error("Error while checking access rights for merchant: {}", e.getMessage());
        }
        return false;
    }

    private String generateHMAC(String data, String key) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac hmacSHA256 = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "HmacSHA256");
        hmacSHA256.init(secretKeySpec);
        byte[] hmacBytes = hmacSHA256.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(hmacBytes);
    }
}
