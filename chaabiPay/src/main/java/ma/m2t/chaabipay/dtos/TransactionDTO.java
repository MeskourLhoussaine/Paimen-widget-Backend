package ma.m2t.chaabipay.dtos;

import jakarta.persistence.Column;
import lombok.Data;
import ma.m2t.chaabipay.entites.PaymentMethod;
import ma.m2t.chaabipay.entites.Transaction;

import java.util.Date;

@Data
public class TransactionDTO {

    private Long transactionId;
    private String orderId;
    private double amount;
    private String currency;
    private String status;
    private Date timestamp;
    private String clientId;
    private String clientName;
    private String clientEmail;
    private String hmac;
    private String notif;
    private Long paymentMethodId;
    private Long merchantId;

    public TransactionDTO() {
    }

    public TransactionDTO(Transaction transaction) {
       this.transactionId = transaction.getTransactionId();
        this.orderId = transaction.getOrderId();
        this.amount = transaction.getAmount();
        this.currency = transaction.getCurrency();
        this.status = transaction.getStatus();
        this.timestamp = new Date(transaction.getTimestamp());
        this.clientId = transaction.getClientId();
        this.clientName = transaction.getClientName();
        this.clientEmail = transaction.getUserEmail();
        this.hmac = transaction.getHmac();
        this.notif = transaction.getNotif();
        if (transaction.getPaymentMethod() != null) {
            this.paymentMethodId = transaction.getPaymentMethod().getPaymentMethodId();
        }
        if (transaction.getMerchant() != null) {
            this.merchantId = transaction.getMerchant().getMerchantId();
        }
    }
}