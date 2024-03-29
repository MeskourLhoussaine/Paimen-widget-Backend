package ma.m2t.chaabipay.entites;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("AMANTY")
@Entity
public class Amnanty extends PaymentMethod{
    private String username;
    private String password;

}
