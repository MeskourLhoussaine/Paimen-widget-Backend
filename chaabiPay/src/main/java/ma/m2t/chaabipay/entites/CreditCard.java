package ma.m2t.chaabipay.entites;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
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
    private String numberCard;
    private String titulairName;
    private Date dateExper;
    private String CVV;
}
