package ma.m2t.chaabipay.services;

import ma.m2t.chaabipay.exceptions.MerchantExceptionNotFound;

import java.util.List;
import java.util.Map;


public interface MerchantMethodePaymentService {

    //Get/////////////////////

    List<Map<String, Object>> getMerchantPaymentMethod(Long merchantId) throws MerchantExceptionNotFound;


    ///****************************************************************************************************

//Update/////////////////////
    void selectPaymentMethodInMerchant(Long merchantId, Long paymentMethodId) throws MerchantExceptionNotFound;

///*************************
}
