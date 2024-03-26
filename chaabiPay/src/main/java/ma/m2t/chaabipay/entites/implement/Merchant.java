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
@Table(name = "merchantes")
public class Merchant  extends GenericEntity {
    private String merchant_id;
    private String merchantName;
    private String merchantDescrip;
    private String merchantHost;
    private String merchantLogo;
    private String callback;
    private String serviceid;
    private String sucretkey; //doit etre ceypter

    @OneToMany(mappedBy = "merchant", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Transaction> transactions;

}
