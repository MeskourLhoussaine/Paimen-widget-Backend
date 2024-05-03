package ma.m2t.chaabipay.controller;

import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ma.m2t.chaabipay.dtos.PaimentMethodeDTO;
import ma.m2t.chaabipay.entites.MerchantMethodePayment;
import ma.m2t.chaabipay.entites.PaymentMethod;
import ma.m2t.chaabipay.exceptions.MerchantExceptionNotFound;
import ma.m2t.chaabipay.services.MerchantMethodePaymentService;
import ma.m2t.chaabipay.services.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    /* utiliser pour voir le status de chaque methode affecter au marchand* important!!!!!!--*/
   @GetMapping("/status/{merchantId}/{paymentMethodId}")
    public boolean findStatusMerchantPaymentByMerchantIdAndPaymentMethodId(@PathVariable Long merchantId, @PathVariable Long paymentMethodId) {
        return merchantMethodePaymentService.findStatusMerchantPaymentByMerchantIdAndPaymentMethodId(merchantId, paymentMethodId);
    }

    /*----------------Update Status merchandMethode----------------*/
    @PutMapping("/updateMerchantMethodStatusByPaymentMethodId/{paymentMethodId}/{merchantId}/{status}")
    public ResponseEntity<String> updateMerchantMethodStatusByPaymentMethodId(
            @PathVariable Long paymentMethodId,
            @PathVariable Long merchantId,
            @PathVariable boolean status) {

        try {
            merchantMethodePaymentService.updateMerchantMethodStatusByPaymentMethodId(paymentMethodId, merchantId, status);
            return ResponseEntity.ok("Merchant method status updated successfully.");
        } catch (MerchantExceptionNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating merchant method status.");
        }
    }

    /*----------------Update Status merchandMethode----------------*/
/*@PutMapping("/updateMerchantMethodStatus/{id}/{status}")
    public void updateMerchantMethodStatus(@PathVariable Long id, @PathVariable boolean status) throws MerchantExceptionNotFound {
        merchantMethodePaymentService.updateMerchantMethodStatus(id, status);
    }*/


}
