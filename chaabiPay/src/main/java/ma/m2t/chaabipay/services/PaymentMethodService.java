package ma.m2t.chaabipay.services;

import ma.m2t.chaabipay.dtos.PaimentMethodeDTO;
import ma.m2t.chaabipay.entites.PaymentMethod;
import ma.m2t.chaabipay.entites.Transaction;

import java.util.List;

public interface PaymentMethodService {
    //CRUD METHODE
    List<PaimentMethodeDTO> listPaimentMethod();
    PaimentMethodeDTO savePaimenMethode(PaimentMethodeDTO paimentMethodeDTO);

    PaimentMethodeDTO updatePaimenMethode(PaimentMethodeDTO paimentMethodeDTO);
    void deletePaimenMethode(PaimentMethodeDTO paimentMethodeDTO);

//############meskour###########"



}
