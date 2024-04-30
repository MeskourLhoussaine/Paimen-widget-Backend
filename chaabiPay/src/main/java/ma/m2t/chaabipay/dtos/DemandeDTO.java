package ma.m2t.chaabipay.dtos;

import lombok.Data;
import ma.m2t.chaabipay.enums.AneeActivite;
import ma.m2t.chaabipay.enums.Formejuridique;
import ma.m2t.chaabipay.enums.Status;

@Data
public class DemandeDTO {

    private Long demandeId;

    // Marchand info
    private String demandeMarchandName;
    private String demandeMarchandDescription;
    private String demandeMarchandPhone;
    private String demandeMarchandHost;
    private String demandeMarchandEmail;
    private String demandeMarchandLogoUrl; //
    private Status demandeMarchandStatus;

    // Marchand info private
    private String demandeMarchandTypeActivite;
    private String demandeMarchandRcIf;
    private String demandeMarchandSiegeAddresse;
    private String demandeMarchandDgName;
    private Formejuridique demandeMarchandFormejuridique;
    private AneeActivite demandeMarchandAnneeActivite;

    // validation
    private Boolean demandeIsAccepted;
    private Boolean demandeIsVerified;
}