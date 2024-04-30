package ma.m2t.chaabipay.services;

import ma.m2t.chaabipay.dtos.DemandeDTO;
import ma.m2t.chaabipay.exceptions.MerchantExceptionNotFound;

import java.util.List;

public interface DemandeService {
    ///****************************************************************************************************
//Post
    DemandeDTO saveNewDemande(DemandeDTO demandeDTO);


    ///*****************************************************************************************************
//Get
    // ( maghatstakhdemch ) katjib li verified o li la
    List<DemandeDTO> getAllDemandes();
    DemandeDTO getDemande(Long demandeId);

    // get (verified=false)
    List<DemandeDTO> getAllDemandesNotVerified();


    ///****************************************************************************************************
//Update
    // Set (verified=true Accepted=false)
    DemandeDTO UpdateDemandeRejected(Long demandeId) throws MerchantExceptionNotFound;

    // Set (verified=true Accepted=true)  and  Save merchand
    DemandeDTO UpdateDemandeAccepted(Long demandeId) throws MerchantExceptionNotFound;

///****************************************************************************************************
//Delete


}