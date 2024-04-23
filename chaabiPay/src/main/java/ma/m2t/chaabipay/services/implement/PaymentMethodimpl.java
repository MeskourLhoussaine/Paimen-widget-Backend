package ma.m2t.chaabipay.services.implement;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.m2t.chaabipay.dtos.PaimentMethodeDTO;
import ma.m2t.chaabipay.entites.*;
import ma.m2t.chaabipay.mappers.ImplementMapers;
import ma.m2t.chaabipay.repositories.MerchantRepository;
import ma.m2t.chaabipay.repositories.PaimentMethodeReposirory;
import ma.m2t.chaabipay.repositories.TransactionRepository;
import ma.m2t.chaabipay.services.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class PaymentMethodimpl implements PaymentMethodService {


    private TransactionRepository transactionRepository;
@Autowired
    private MerchantRepository merchantRepository;

    private PaimentMethodeReposirory paymentMethodRepository;


    private PaimentMethodeReposirory paimentMethodeReposirory;
    private ImplementMapers dtoMapper;

    @Override
    public List<PaimentMethodeDTO> listPaimentMethod() {
        List<PaymentMethod> paymentMethods = paimentMethodeReposirory.findAll();
        List<PaimentMethodeDTO> paimentMethodeDTOS = paymentMethods.stream()
                .map(paymentMethod -> dtoMapper.fromPaimentMethode(paymentMethod))
                .collect(Collectors.toList());
    /*
    List<MerchantDTO> merchantDTOS = new ArrayList<>();
    for (Merchant merchant : merchants) {
        MerchantDTO merchantDTO = dtoMapper.fromMerchant(merchant);
        merchantDTOS.add(merchantDTO);
    }
    */
        return paimentMethodeDTOS;
    }


    @Override
    public PaimentMethodeDTO savePaimenMethode(PaimentMethodeDTO paimentMethodeDTO) {
        if (paimentMethodeDTO == null) {
            log.error("PaimentMethodeDTO is null. Cannot save null object.");
            throw new IllegalArgumentException("PaimentMethodeDTO cannot be null");
        }
        log.info("Saving new Payment Method");
        PaymentMethod paimentMethode = dtoMapper.fromPaimentMethodeDTO(paimentMethodeDTO);
        // Utilisation de Optional pour éviter les NullPointerException
        Optional<PaymentMethod> savedPaimentMethode = Optional.ofNullable(paimentMethodeReposirory.save(paimentMethode));
        return savedPaimentMethode.map(dtoMapper::fromPaimentMethode)
                .orElseThrow(() -> new RuntimeException("Error while saving payment method"));
    }


    @Override
    public PaimentMethodeDTO updatePaimenMethode(PaimentMethodeDTO paimentMethodeDTO) {
        return null;
    }

    @Override
    public void deletePaimenMethode(PaimentMethodeDTO paimentMethodeDTO) {

    }

    //########meskour getgetPaimentMethodeBymerchanId############23/04/2024 merchantdasboard


    /*#####################sevice */

}