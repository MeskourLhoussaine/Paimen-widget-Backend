package ma.m2t.chaabipay.services.implement;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ma.m2t.chaabipay.dtos.MerchantDTO;
import ma.m2t.chaabipay.dtos.PaimentMethodeDTO;
import ma.m2t.chaabipay.entites.MerchantMethodePayment;
import ma.m2t.chaabipay.entites.Merchant;
import ma.m2t.chaabipay.entites.PaymentMethod;
import ma.m2t.chaabipay.enums.Status;
import ma.m2t.chaabipay.exceptions.MerchantExceptionNotFound;
import ma.m2t.chaabipay.mappers.ImplementMapers;
import ma.m2t.chaabipay.repositories.MerchantMethodePaymentRepository;
import ma.m2t.chaabipay.repositories.MerchantRepository;
import ma.m2t.chaabipay.repositories.PaimentMethodeReposirory;
import ma.m2t.chaabipay.services.MerchantService;
import org.apache.commons.codec.binary.Hex;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@CrossOrigin("*")
@Slf4j

public class MerchantImplement implements MerchantService {
@Autowired
    private ImplementMapers dtoMapper;
    @Autowired
    private MerchantRepository merchantRepository;
    @Autowired
    private PaimentMethodeReposirory paimentMethodeReposirory;
    @Autowired
    private MerchantMethodePaymentRepository marchandMethodePaiementRepository;

    public MerchantImplement() {

    }

    /************----< get ALL Marchant >-----*******************/
                             /**--#-- */
    @Override
    public List<MerchantDTO> listMerchantes() {
        List<Merchant> marchands = merchantRepository.findAll(Sort.by(Sort.Direction.ASC, "merchantId"));
        List<MerchantDTO> marchandsDTO = new ArrayList<>();
        for (Merchant m : marchands) {
            marchandsDTO.add(dtoMapper.fromMerchant(m));
        }
        return marchandsDTO;
    }
    /*******----< Méthode pour Ajouter le marchand avec un cle secret encrypter >-----**********/
                                  /**--#-- */
    @Override
    public MerchantDTO saveMerchant(MerchantDTO merchantDTO) {
        Merchant marchand = dtoMapper.fromMerchantDTO(merchantDTO);

        String secretKey = generateSecretKey();
        marchand.setSucretkey(secretKey);

        Merchant savedMarchand = merchantRepository.save(marchand);

        return dtoMapper.fromMerchant(savedMarchand);
    }

    /************----< Méthode pour Modifier le marchand >-----*******************/
                                   /**--#-- */
@Override
public MerchantDTO updateMerchant(MerchantDTO merchantDTO) {
    // Récupérer le marchand existant par son ID
    Merchant marchandToUpdate = dtoMapper.fromMerchantDTO(merchantDTO);

    Merchant existingMarchand = merchantRepository.findById(merchantDTO.getMerchantId())
            .orElseThrow(() -> new EntityNotFoundException("Merchant not found"));
    // Update the existing marchand with the values from the DTO
    existingMarchand.setMerchantName(marchandToUpdate.getMerchantName());
    existingMarchand.setMerchantDescrip(marchandToUpdate.getMerchantDescrip());
    existingMarchand.setMarchandPhone(marchandToUpdate.getMarchandPhone());
    existingMarchand.setMerchantHost(marchandToUpdate.getMerchantHost());
    existingMarchand.setMarchandEmail(marchandToUpdate.getMarchandEmail());
    existingMarchand.setMerchantUrl(marchandToUpdate.getMerchantUrl());
    existingMarchand.setMarchandStatus(marchandToUpdate.getMarchandStatus());
    // Marchand info private
    existingMarchand.setMarchandTypeActivite(marchandToUpdate.getMarchandTypeActivite());
    existingMarchand.setMarchandRcIf(marchandToUpdate.getMarchandRcIf());
    existingMarchand.setMarchandSiegeAddresse(marchandToUpdate.getMarchandSiegeAddresse());
    existingMarchand.setMarchandDgName(marchandToUpdate.getMarchandDgName());
    existingMarchand.setMarchandFormejuridique(marchandToUpdate.getMarchandFormejuridique());
    existingMarchand.setMarchandAnneeActivite(marchandToUpdate.getMarchandAnneeActivite());

    // Enregistrer la mise à jour du marchand dans la base de données
    Merchant updatedMarchand = merchantRepository.save(existingMarchand);
    // Mapper l'entité mise à jour vers le DTO et le retourner
    return dtoMapper.fromMerchant(updatedMarchand);
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
    public void deleteMerchantById(Long merchantId) {
        merchantRepository.deleteById(merchantId);
    }

    @Override
    public int findMesrchantIdbyMerchantName(String merchantName) {
        // Récupérer le marchand correspondant au nom donné
        Merchant merchant = merchantRepository.findByMerchantName(merchantName)
                .orElseThrow(() -> new EntityNotFoundException("Merchant not found with name: " + merchantName));

        // Retourner l'ID du marchand
        return merchant.getMerchantId().intValue(); // Si l'ID est de type Long, vous pouvez le convertir en int si nécessaire
    }


    @Override
    public MerchantDTO findMerchantById(Long id) {
        Optional<Merchant> optionalMerchant = merchantRepository.findById(id);
        if (optionalMerchant.isPresent()) {
            return dtoMapper.fromMerchant(optionalMerchant.get());
        } else {
            throw new EntityNotFoundException("Merchant not found with ID: " + id);
        }
    }

    @Override
    public List<MerchantDTO> getAllMerchantsByMethod(Long methodId) {
        return null;
    }


    /******************************************************************************
     * ********************************************************************/
    @Override
    public String generateAndSaveSecretKey(String merchantName, String merchantHost, String merchantDescrip, String merchantLogo, String callback,String accessKey, String serviceid ) {
        // Générer une clé secrète aléatoire
        String secretKey = generateSecretKey();

        // Créer un nouveau marchand avec toutes les informations fournies
        Merchant merchant = new Merchant();
        merchant.setMerchantName(merchantName);
        merchant.setMerchantHost(merchantHost);
        merchant.setMerchantDescrip(merchantDescrip);
        merchant.setMerchantUrl(merchantLogo);
        merchant.setCallback(callback);
        merchant.setServiceid(serviceid);
        merchant.setSucretkey(secretKey);
        merchant.setAccessKey(accessKey);// Attention à la faute de frappe dans le nom du setter
       // merchant.getMarchandPhone(marchandPhone);

        // Enregistrer le marchand dans la base de données
        merchantRepository.save(merchant);

        // Retourner la clé secrète générée
        return secretKey;
    }
    /******************************************************************************
     * ********************************************************************/


@Override
public void associerMethodesPaiementToMerchant(Long marchandId, Set<Long> methodePaiementIds) throws MerchantExceptionNotFound {
    Merchant merchant = merchantRepository.findById(marchandId)
            .orElseThrow(() -> new MerchantExceptionNotFound("Merchant Not found"));

    // Retrieve the existing associations for this merchant
    List<MerchantMethodePayment> existingAssociations = marchandMethodePaiementRepository.findByMerchantMerchantId(marchandId);
    Set<Long> existingPaymentMethodIds = new HashSet<>();
    for (MerchantMethodePayment association : existingAssociations) {
        existingPaymentMethodIds.add(association.getPaymentMethod().getPaymentMethodId());
    }

    // Iterate over the chosen payment method IDs
    for (Long paymentMethodId : methodePaiementIds) {
        if (!existingPaymentMethodIds.contains(paymentMethodId)) {
            // Retrieve the payment method from the database
            PaymentMethod paymentMethod = paimentMethodeReposirory.findById(paymentMethodId)
                    .orElseThrow(() -> new IllegalArgumentException("Payment Method not found"));

            // Create a new MerchantMethods entity
            MerchantMethodePayment merchantMethods = new MerchantMethodePayment();
            merchantMethods.setMerchant(merchant);
            merchantMethods.setPaymentMethod(paymentMethod);
            merchantMethods.setStatus(true);

            marchandMethodePaiementRepository.save(merchantMethods);
        } else {
            // Update existing association if it exists
            for (MerchantMethodePayment existingAssociation : existingAssociations) {
                if (existingAssociation.getPaymentMethod().getPaymentMethodId().equals(paymentMethodId)) {
                    existingAssociation.setStatus(true); // Update isSelected to true
                    marchandMethodePaiementRepository.save(existingAssociation);
                    break;
                }
            }
        }
    }

    // Print out the names of associated payment methods
    System.out.print("Merchant " + merchant.getMerchantName() + " has the following payment methods: ");
    List<PaymentMethod> paymentMethods = paimentMethodeReposirory.findAllById(methodePaiementIds);
    for (PaymentMethod p : paymentMethods) {
        System.out.print(p.getName() + ", ");
    }
    System.out.println();
}
//**END**//

    /****************Méthode pour générer une clé secrète aléatoire**************************
     * ********************************************************************/

    // Méthode pour générer une clé secrète aléatoire
    // Generate a secret key
    public String generateSecretKey() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        return new BigInteger(1, randomBytes).toString(16);
    }

    //Hash
    public static String hashSecretKey(String secretKey) {
        return BCrypt.hashpw(secretKey, BCrypt.gensalt());
    }
    public static boolean verifySecretKey(String secretKey, String hashedKey) {
        return BCrypt.checkpw(secretKey, hashedKey);
    }


    @Override
    public boolean checkAccessRights(String hostname, String accessKey, String merchantId, String orderId,
                                     double amount, String currency, String hmac) {
        List<Merchant> marchands = merchantRepository.findAll();

        for (Merchant marchand : marchands) {
            //verify the dehash secret key if equals to secretKey provided
            if ( marchand.getMerchantId().equals(Long.parseLong(merchantId)) &&  marchand.getMerchantHost().equals(hostname) && marchand.getAccessKey().equals(accessKey) && marchand.getMarchandStatus().equals(Status.Active)) {
                String generatedHmac = generateHmac(merchantId, orderId, amount, currency, marchand.getSucretkey());
                if (hmac.equals(generatedHmac)) {
                    System.out.println("HMAC Permission granted."+generatedHmac+"......"+ marchand.getSucretkey());
                    return true;
                } else {
                    System.out.println("......"+merchantId+"......");

                    System.out.println("HMAC verification failed."+generatedHmac+"......"+ marchand.getSucretkey());
                    return false;
                }
            }
        }

        System.out.println("Marchand not found with provided hostname and secret key.");
        return false;
    }



    public String generateHmac(String merchantId, String orderId, double amount, String currency, String secretKey) {
        String data = merchantId + ':' + orderId + ':' + amount + ':' + currency;
        return hmacDigest(data, secretKey);

    }

    private String hmacDigest(String data, String secretKey) {
        try {
            byte[] keyBytes = secretKey.getBytes();
            SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacSHA1");

            // Get an hmac_sha1 Mac instance and initialize with the signing key
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);

            // Compute the hmac on input data bytes
            byte[] rawHmac = mac.doFinal(data.getBytes());

            // Convert raw bytes to Hex
            byte[] hexBytes = new Hex().encode(rawHmac);

            //  Covert array of Hex bytes to a String
            return new String(hexBytes, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /*usin for aytenticated by marchand*/



}
