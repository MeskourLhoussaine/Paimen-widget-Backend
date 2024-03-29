package ma.m2t.chaabipay.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Table(name = "paymentMethods")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "payment_method_type", discriminatorType = DiscriminatorType.STRING)
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String iconUrl;
    @OneToMany(mappedBy = "paymentMethod", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Transaction> transactions;
}
