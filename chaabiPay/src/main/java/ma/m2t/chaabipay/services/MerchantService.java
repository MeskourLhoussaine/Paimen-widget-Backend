package ma.m2t.chaabipay.services;

import ma.m2t.chaabipay.dtos.MerchantDTO;
import ma.m2t.chaabipay.entites.PaymentMethod;

import java.util.List;
import java.util.Set;

public interface MerchantService {

    List<MerchantDTO> listMerchantes();
    MerchantDTO saveMerchant(MerchantDTO merchantDTO);

    MerchantDTO updateMerchant(MerchantDTO merchantDTO);
    void deleteMerchant(MerchantDTO merchantDTO);

    //Api personaliser
    // Nouvelle méthode pour générer et enregistrer la clé secrète pour un marchand
    String generateAndSaveSecretKey(String merchantName, String merchantHost,String merchantDescrip,String merchantLogo,String callback,String serviceid );




    void associerMethodePaiement(Long marchandId, Set<Long> methodePaiementIds);
}
