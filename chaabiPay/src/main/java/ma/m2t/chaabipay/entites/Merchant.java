package ma.m2t.chaabipay.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import ma.m2t.chaabipay.enums.AneeActivite;
import ma.m2t.chaabipay.enums.Formejuridique;
import ma.m2t.chaabipay.enums.Status;

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
    // Marchand info
    private String merchantName;

    private String merchantDescrip;
    private String merchantHost; //le lien
    private String merchantUrl; //logoUrl
    private String marchandPhone;//+
    private String marchandEmail;//+
    @Enumerated(EnumType.STRING)
    private Status marchandStatus;//+

    // Marchand info private//+
    private String marchandTypeActivite;
    private String marchandRcIf;
    private String marchandSiegeAddresse;
    private String marchandDgName;
    @Enumerated(EnumType.STRING)
    private Formejuridique marchandFormejuridique;
    @Enumerated(EnumType.STRING)
    private AneeActivite marchandAnneeActivite;


    //Callback
     private String callback;
    // Service ID
    private String serviceid;
    //  // Access Key
    private String accessKey;
    //Secret key (Hashed)
    private String sucretkey;


    @OneToMany(mappedBy = "merchant")
    // Ignorer cette propriété lors de la sérialisation JSON pour éviter les boucles infinies
    private List<Transaction> transactions;

    @ManyToMany
    @JoinTable(name = "merchant_paymentMethod",
            joinColumns = @JoinColumn(name = "merchantId"),
            inverseJoinColumns = @JoinColumn(name = "paymentMethodId"))
    private List<PaymentMethod> paymentMethods;
}
