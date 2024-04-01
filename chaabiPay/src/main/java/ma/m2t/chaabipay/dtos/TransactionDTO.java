package ma.m2t.chaabipay.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class TransactionDTO {
    private String orderId;
    private double amount;
    private String currency;
    private String status;
    private Date timestamp;
    private String clientId;
    private String clientName;
    private String userEmail;
    private String hmac;
    private String Notif;
    private PaimentMethodeDTO paymentMethod;
    private MerchantDTO merchant;
}
