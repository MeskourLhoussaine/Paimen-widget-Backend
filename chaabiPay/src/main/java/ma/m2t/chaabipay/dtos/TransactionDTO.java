package ma.m2t.chaabipay.dtos;

import lombok.Data;

import java.util.Date;
@Data
public class TransactionDTO {
    private String orderId;// id commande
    private double amount;
    private String currency;// dh
    private String status;
    private Date timestamp;

    // client
    private String clientId;
    private String clientName;
    private String userEmail;
    // hachage
    private String hmac;
    //
    private String Notif;
}
