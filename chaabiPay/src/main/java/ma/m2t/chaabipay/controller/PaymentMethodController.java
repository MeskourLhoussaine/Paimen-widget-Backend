package ma.m2t.chaabipay.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ma.m2t.chaabipay.dtos.PaimentMethodeDTO;
import ma.m2t.chaabipay.exceptions.MerchantExceptionNotFound;
import ma.m2t.chaabipay.services.MerchantMethodePaymentService;
import ma.m2t.chaabipay.services.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/merchant_methods")
@AllArgsConstructor

public class PaymentMethodController {
    @Autowired
    private  MerchantMethodePaymentService merchantMethodePaymentService;
    @PutMapping("/{merchantId}/payment-method/{paymentMethodId}")
    public void selectPaymentMethodInMerchant(@PathVariable Long merchantId, @PathVariable Long paymentMethodId) throws MerchantExceptionNotFound {
        merchantMethodePaymentService.selectPaymentMethodInMerchant(merchantId, paymentMethodId);
    }

}
