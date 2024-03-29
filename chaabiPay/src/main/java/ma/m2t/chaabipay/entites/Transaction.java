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
public class Transaction{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Order * * * * * *
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
    @ManyToOne(cascade = CascadeType.ALL)
    private PaymentMethod paymentMethod;

    //@ManyToOne(cascade = CascadeType.MERGE)
    @ManyToOne(cascade = CascadeType.ALL)
    private Merchant merchant;
}
