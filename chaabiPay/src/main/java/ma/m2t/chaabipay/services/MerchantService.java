package ma.m2t.chaabipay.services;

import ma.m2t.chaabipay.dtos.MerchantDTO;
import ma.m2t.chaabipay.entites.PaymentMethod;
import ma.m2t.chaabipay.exceptions.MerchantExceptionNotFound;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface MerchantService {
                     //##############POST###################
MerchantDTO saveMerchant(MerchantDTO merchantDTO);
    // Nouvelle méthode pour générer et enregistrer la clé secrète pour un marchand
String generateAndSaveSecretKey(String merchantName, String merchantHost,String merchantDescrip,String merchantLogo,String callback,String accessKey,String serviceid );
void associerMethodesPaiementToMerchant(Long marchandId, Set<Long> methodePaiementIds) throws MerchantExceptionNotFound;

                       //##############GET###################
    List<MerchantDTO> listMerchantes();
    MerchantDTO findMerchantById(Long id) throws MerchantExceptionNotFound;
    List<MerchantDTO> getAllMerchantsByMethod(Long methodId);

    //verifier les permission
     boolean checkAccessRights(String hostname, String accessKey, String merchantId, String orderId,
                               double amount, String currency, String hmac);

     //####################PUT#########
    MerchantDTO updateMerchant(MerchantDTO merchantDTO);
    void deleteMerchant(MerchantDTO merchantDTO);
    void deleteMerchantById(Long merchantId);

    /*usin for Autenticated by marchand */
int findMesrchantIdbyMerchantName(String merchantName);
}
