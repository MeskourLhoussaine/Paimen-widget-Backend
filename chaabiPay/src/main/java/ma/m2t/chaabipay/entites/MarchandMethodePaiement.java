package ma.m2t.chaabipay.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "marchand_methodepaiement")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MarchandMethodePaiement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "marchand_id")
    private Merchant marchand;

    @ManyToOne
    @JoinColumn(name = "methodepaiement_id")
    private PaymentMethod methodePaiement;

    private boolean etat;

}
