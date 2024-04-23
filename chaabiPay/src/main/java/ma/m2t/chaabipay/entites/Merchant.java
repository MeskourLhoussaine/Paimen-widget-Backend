package ma.m2t.chaabipay.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "merchants")
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long merchantId;
    private String merchantName;
    private String merchantDescrip;
    private String merchantHost; //le lien
    private String merchantUrl; //logoUrl
    private String callback; //
    private String serviceid;
    private String accessKey;
    private String sucretkey; //Doit etre crepty

    @OneToMany(mappedBy = "merchant")
    // Ignorer cette propriété lors de la sérialisation JSON pour éviter les boucles infinies
    private List<Transaction> transactions;

    @ManyToMany
    @JoinTable(name = "merchant_paymentMethod",
            joinColumns = @JoinColumn(name = "merchantId"),
            inverseJoinColumns = @JoinColumn(name = "paymentMethodId"))
    private List<PaymentMethod> paymentMethods;
}
