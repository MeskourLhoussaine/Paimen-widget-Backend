package ma.m2t.chaabipay.entites;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("CARD")
@Entity
public class CreditCard extends PaymentMethod{
    private Long cartId;
    private String numberCard;
    private String titulairName;
    private Date dateExper;
    private String cvv;


}
