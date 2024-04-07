package ma.m2t.chaabipay.entites;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ma.m2t.chaabipay.dtos.CreditCardDTO;
import ma.m2t.chaabipay.dtos.MerchantDTO;
import ma.m2t.chaabipay.dtos.TokenDTO;

import java.util.Date;

@Data
public class TransactionRequest {

    // Assurez-vous d'importer ou de déclarer correctement cette classe
    private String paymentMethodType; // Renommez la variable conformément aux conventions de nommage
    private String orderId;
    private double amount;
    private String currency;
    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date timestamp;

    private String clientId;
    private String clientName;
    private String userEmail;
    private String hmac;
    private String notif;

    private CreditCardDTO creditCardDTO; // Ajout de la référence à CreditCardDTO
    private TokenDTO tokenDTO; // Ajout de la référence à TokenDTO

}
