package ma.m2t.chaabipay.controller;

import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ma.m2t.chaabipay.dtos.PaimentMethodeDTO;
import ma.m2t.chaabipay.entites.PaymentMethod;
import ma.m2t.chaabipay.exceptions.MerchantExceptionNotFound;
import ma.m2t.chaabipay.services.MerchantMethodePaymentService;
import ma.m2t.chaabipay.services.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/merchant_methods")
@AllArgsConstructor

public class PaymentMethodController {
    @Autowired
    private  MerchantMethodePaymentService merchantMethodePaymentService;
    @Autowired
    private PaymentMethodService paymentMethodService;
    @PutMapping("/{merchantId}/payment-method/{paymentMethodId}")
    public void selectPaymentMethodInMerchant(@PathVariable Long merchantId, @PathVariable Long paymentMethodId) throws MerchantExceptionNotFound {
        merchantMethodePaymentService.selectPaymentMethodInMerchant(merchantId, paymentMethodId);
    }
    //######liste paimentMethode
    @GetMapping("/findAll")
    public List<PaimentMethodeDTO> listPaimentMethod() {
        return paymentMethodService.listPaimentMethod();
    }
    /*utiliser pour front Merchant (details) Transaction*/
    @GetMapping("/findById/{paymentMethodId}")
    public PaimentMethodeDTO getPymentMethodeById(@PathVariable Long paymentMethodId) {
        return paymentMethodService.getPymentMethodeById(paymentMethodId);
    }
}
