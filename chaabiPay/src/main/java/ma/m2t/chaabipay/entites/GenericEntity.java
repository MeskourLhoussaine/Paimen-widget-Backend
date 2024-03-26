package ma.m2t.chaabipay.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import ma.m2t.chaabipay.metier.IAbstract;

import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public abstract class GenericEntity implements Serializable, IAbstract {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    @JsonIgnore
    private Date addDate = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = true)
    @JsonIgnore
    private Date updateDate = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = true)
    @JsonIgnore
    private Date deleteDate = null;
}
