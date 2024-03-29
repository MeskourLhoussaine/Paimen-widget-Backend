package ma.m2t.chaabipay.entites;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("TOKEN")
@Entity
public class Token extends PaymentMethod {
    private String tokenId;
    private String tokenResponse;
    private String tokenName;
    private String tokenEmail;
}