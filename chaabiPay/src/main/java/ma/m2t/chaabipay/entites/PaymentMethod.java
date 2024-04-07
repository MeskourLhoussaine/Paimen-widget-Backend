package ma.m2t.chaabipay.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import ma.m2t.chaabipay.entites.Transaction;

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
    private Long paymentMethodId;
    private String name;
    private String description;
    private String iconUrl;


    @OneToMany(mappedBy = "paymentMethod")
    private List<Transaction> transactions;

    @ManyToMany(mappedBy = "paymentMethods")
    private List<Merchant> merchants;
}
