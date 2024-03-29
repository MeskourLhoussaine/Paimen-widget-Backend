package ma.m2t.chaabipay.entites;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("PAYDIRECT")
@Entity
public class PayDirect extends PaymentMethod{
    private String numContrat;
    private String nomMondataire;
    private String cin;
    private String tel;
    private String email;
}
