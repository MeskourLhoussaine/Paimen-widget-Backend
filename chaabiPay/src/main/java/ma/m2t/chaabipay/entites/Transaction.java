package ma.m2t.chaabipay.entites;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.m2t.chaabipay.entites.PaymentMethod;
import ma.m2t.chaabipay.entites.Merchant;


import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions")

public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    private String orderId;
    private double amount;
    private String currency;
    private String status;

   // @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private String timestamp;

    private String clientId;
    private String clientName;
    private String clientEmail;
    private String hmac;
    private String notif;

    
    @ManyToOne(fetch = FetchType.LAZY)
    private PaymentMethod paymentMethod;

    @ManyToOne(fetch = FetchType.LAZY)
    private Merchant merchant;
}
