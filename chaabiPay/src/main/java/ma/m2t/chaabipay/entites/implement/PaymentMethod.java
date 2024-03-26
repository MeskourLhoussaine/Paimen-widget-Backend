package ma.m2t.chaabipay.entites.implement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import ma.m2t.chaabipay.entites.GenericEntity;

import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Table(name = "paymentMethods")
public class PaymentMethod extends GenericEntity {
    private String name;
    private String description;
    private String iconUrl;
    @OneToMany(mappedBy = "paymentMethod", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Transaction> transactions;
}
