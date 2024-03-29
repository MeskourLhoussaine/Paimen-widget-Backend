package ma.m2t.chaabipay.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ma.m2t.chaabipay.dtos.PaimentMethodeDTO;
import ma.m2t.chaabipay.services.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/methods")
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethodController {
    @Autowired
    private PaymentMethodService paymentMethodService;
@GetMapping("/findAll")
    public List<PaimentMethodeDTO> listPaimentMethod() {
        return paymentMethodService.listPaimentMethod();
    }

    @PostMapping("/save")
    public PaimentMethodeDTO savePaimenMethode(PaimentMethodeDTO paimentMethodeDTO) {
        return paymentMethodService.savePaimenMethode(paimentMethodeDTO);
    }

    public PaimentMethodeDTO updatePaimenMethode(PaimentMethodeDTO paimentMethodeDTO) {
        return paymentMethodService.updatePaimenMethode(paimentMethodeDTO);
    }

    public void deletePaimenMethode(PaimentMethodeDTO paimentMethodeDTO) {
        paymentMethodService.deletePaimenMethode(paimentMethodeDTO);
    }
}
