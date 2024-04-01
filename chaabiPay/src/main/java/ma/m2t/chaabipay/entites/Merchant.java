package ma.m2t.chaabipay.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Table(name = "merchants")
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String merchantName;
    private String merchantDescrip;
    private String merchantHost; //le lien
    private String merchantLogo;
    private String callback; //
    private String serviceid;
    private String sucretkey; //Doit etre crepty

    @OneToMany(mappedBy = "merchant", fetch = FetchType.LAZY)
    @JsonIgnore // Ignorer cette propriété lors de la sérialisation JSON pour éviter les boucles infinies
    private List<Transaction> transactions;
}
