package ma.m2t.chaabipay.entites;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderId;// id commande
    private double amount;
    private String currency;// dh
    private String status;
    private Date timestamp;

    private String clientId;
    private String clientName;
    private String userEmail;
    private String hmac;
    private String notif;

    @ManyToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;
}
