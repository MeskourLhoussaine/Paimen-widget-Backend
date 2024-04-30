package ma.m2t.chaabipay.entites;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.m2t.chaabipay.enums.AneeActivite;
import ma.m2t.chaabipay.enums.Formejuridique;
import ma.m2t.chaabipay.enums.Status;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Demande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long demandeId;

    // Marchand info
    private String demandeMarchandName;
    private String demandeMarchandDescription;
    private String demandeMarchandPhone;
    private String demandeMarchandHost;
    private String demandeMarchandEmail;
    private String demandeMarchandLogoUrl; //
    @Enumerated(EnumType.STRING)
    private Status demandeMarchandStatus;

    // Marchand info private
    private String demandeMarchandTypeActivite;
    private String demandeMarchandRcIf;
    private String demandeMarchandSiegeAddresse;
    private String demandeMarchandDgName;
    @Enumerated(EnumType.STRING)
    private Formejuridique demandeMarchandFormejuridique;
    @Enumerated(EnumType.STRING)
    private AneeActivite demandeMarchandAnneeActivite;

    // validation
    @Column(columnDefinition = "boolean default false")
    private Boolean demandeIsAccepted;

    @Column(columnDefinition = "boolean default false")
    private Boolean demandeIsVerified;
}