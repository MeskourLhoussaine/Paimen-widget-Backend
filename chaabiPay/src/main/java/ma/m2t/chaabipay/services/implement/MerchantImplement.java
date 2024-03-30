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
/*
    @Override
    public void associerMethodesPaiement(Long marchandId, Set<Long> methodePaiementIds) {
        Optional<Merchant> optionalMerchant = merchantRepository.findById(marchandId);
        if (optionalMerchant.isPresent()) {
            Merchant merchant = optionalMerchant.get();
            Set<PaymentMethod> methods = merchant.getMethodesPaiements();

            for (Long methodePaiementId : methodePaiementIds) {
                Optional<PaymentMethod> optionalMethod = paimentMethodeReposirory.findById(methodePaiementId);
                if (optionalMethod.isPresent()) {
                    PaymentMethod method = optionalMethod.get();
                    methods.add(method);
                } else {
                    // Gérer le cas où la méthode de paiement n'existe pas
                }
            }
            merchant.setMethodesPaiements(methods);
            merchantRepository.save(merchant);
        } else {
            // Gérer le cas où le marchand n'existe pas
        }
    }
*/

@Override
public void associerMethodesPaiement(Long marchandId, Set<Long> methodePaiementIds) {
    // Récupérer le marchand
    Merchant merchant = merchantRepository.findById(marchandId)
            .orElseThrow(() -> new RuntimeException("Marchand non trouvé"));

    // Parcourir les IDs des méthodes de paiement
    for (Long methodePaiementId : methodePaiementIds) {
        // Récupérer la méthode de paiement
        PaymentMethod methodePaiement = paimentMethodeReposirory.findById(methodePaiementId)
                .orElseThrow(() -> new RuntimeException("Méthode de paiement non trouvée"));

        // Créer une instance de l'entité associant le marchand et la méthode de paiement avec l'état par défaut false
        MarchandMethodePaiement marchandMethodePaiement = new MarchandMethodePaiement();
        marchandMethodePaiement.setMarchand(merchant);
        marchandMethodePaiement.setMethodePaiement(methodePaiement);
        marchandMethodePaiement.setEtat(false); // état par défaut

        // Enregistrer l'association dans la base de données
        marchandMethodePaiementRepository.save(marchandMethodePaiement);
    }
}
/*
    @Override
    public void associerMethodePaiement(Long marchandId, Set<Long> methodePaiementIds) {
        Merchant merchant = merchantRepository.findById(marchandId)
                .orElseThrow(() -> new NotFoundException("Marchand non trouvé avec l'ID : " + marchandId));

        Set<PaymentMethod> methodesPaiement = new HashSet<>();

        for (Long methodePaiementId : methodePaiementIds) {
            PaymentMethod methodePaiement = paimentMethodeReposirory.findById(methodePaiementId)
                    .orElseThrow(() -> new NotFoundException("Méthode de paiement non trouvée avec l'ID : " + methodePaiementId));
            methodesPaiement.add(methodePaiement);
        }

        merchant.setMethodesPaiement(methodesPaiement);

        merchantRepository.save(merchant);
    }
*/

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
}
