package ma.m2t.chaabipay.services;

import ma.m2t.chaabipay.dtos.PaimentMethodeDTO;

import java.util.List;

public interface PaymentMethodService {
    //CRUD METHODE
    List<PaimentMethodeDTO> listPaimentMethod();
    PaimentMethodeDTO savePaimenMethode(PaimentMethodeDTO paimentMethodeDTO);

    PaimentMethodeDTO updatePaimenMethode(PaimentMethodeDTO paimentMethodeDTO);
    void deletePaimenMethode(PaimentMethodeDTO paimentMethodeDTO);

    //APIs personaliser

}