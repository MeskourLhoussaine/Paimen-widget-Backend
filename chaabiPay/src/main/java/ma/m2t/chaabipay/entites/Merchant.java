package ma.m2t.chaabipay.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Table(name = "merchantes")

public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String merchantName;
    private String merchantDescrip;
    private String merchantHost; //le lien
    private String merchantLogo;
    private String callback; //
    private String serviceid;
    private String sucretkey; //Doit etre crepty

    @OneToMany(mappedBy = "merchant", fetch = FetchType.EAGER)
    @JsonIgnore // Ignorer cette propriété lors de la sérialisation JSON pour éviter les boucles infinies
    private List<Transaction> transactions;
/*
    @ManyToMany
    @JoinTable(
            name = "marchand_methodepaiement",
            joinColumns = @JoinColumn(name = "marchand_id"),
            inverseJoinColumns = @JoinColumn(name = "methodepaiement_id"))
    private Set<PaymentMethod> methodesPaiements= new HashSet<>();

*/


}