package ma.m2t.chaabipay.services;

import ma.m2t.chaabipay.entites.MerchantMethodePayment;
import ma.m2t.chaabipay.exceptions.MerchantExceptionNotFound;

import java.util.List;
import java.util.Map;


public interface MerchantMethodePaymentService {


    List<Map<String, Object>> getMerchantPaymentMethod(Long merchantId) throws MerchantExceptionNotFound;
                /*********----< status de la methode affectue au merchant >----*******/
                                            /**---#---**/
    void selectPaymentMethodInMerchant(Long merchantId, Long paymentMethodId) throws MerchantExceptionNotFound;

                /**********----< get status de la methode affectue au merchant >----*******/
                                           /**---#---**/
    boolean findStatusMerchantPaymentByMerchantIdAndPaymentMethodId(Long merchantId, Long paymentMethodId);

                /**********----------< update status >---------- ****************/
                                         /**---#---**/
    void updateMerchantMethodStatusByPaymentMethodId(Long paymentMethodId,Long merchantId,  boolean status) throws MerchantExceptionNotFound;;

}
