package ma.m2t.chaabipay.dtos;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import ma.m2t.chaabipay.enums.AneeActivite;
import ma.m2t.chaabipay.enums.Formejuridique;
import ma.m2t.chaabipay.enums.Status;

@Data
public class MerchantDTO {
    private Long merchantId;
    // Marchand info
    private String merchantName;

    private String merchantDescrip;
    private String merchantHost; //le lien
    private String merchantUrl; //logoUrl
    private String marchandPhone;//+
    private String marchandEmail;//+
    @Enumerated(EnumType.STRING)
    private Status marchandStatus;//+

    // Marchand info private//+
    private String marchandTypeActivite;
    private String marchandRcIf;
    private String marchandSiegeAddresse;
    private String marchandDgName;
    @Enumerated(EnumType.STRING)
    private Formejuridique marchandFormejuridique;
    @Enumerated(EnumType.STRING)
    private AneeActivite marchandAnneeActivite;


    //Callback
    private String callback;
    // Service ID
    private String serviceid;
    //  // Access Key
    private String accessKey;
    //Secret key (Hashed)
    private String sucretkey;

}
