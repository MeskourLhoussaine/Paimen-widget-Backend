package ma.m2t.chaabipay.entites.implement;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import ma.m2t.chaabipay.entites.GenericEntity;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Table(name = "transactions")
public class Transaction extends GenericEntity {
    private Long PaymentID;

    // Merchand * * -
    private String merchandId;
    private String merchandName;
    private String merchandDescription;

    // Order * * * * * *
    private String orderId;
    private double amount;
    private String currency;
    private String status;
    private long timestamp;

    // User * * *
    private String userId;
    private String userName;
    private String userEmail;
    @ManyToOne(cascade = CascadeType.MERGE)
    private PaymentMethod paymentMethod;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Merchant merchant;
}
