package ma.m2t.chaabipay.services;

import ma.m2t.chaabipay.entites.Token;

public interface TokenService {


    ///****************************************************************************************************
//Post
    Token saveNewToken(Token token);
    void callback();

    ///****************************************************************************************************
//Get
    String generateToken(
            String serviceId,
            String organismId,
            String expirationDate,
            String requestDate,
            String checkSum,
            String tokenStatus,
            String orderId,
            String orderAmount,
            String customerName,
            String customerMail,
            String currency,
            String marchandId
    ) throws Exception ;


    String CallbackTokenReceived();

    String CallbackTokenPay();

///****************************************************************************************************
//Update

///****************************************************************************************************
//Delete
}
