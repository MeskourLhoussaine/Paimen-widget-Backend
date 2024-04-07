package ma.m2t.chaabipay.dtos;

import lombok.Data;

@Data
public class MerchantDTO {
    private Long merchantId;
    private Long id;
    private String merchantName;
    private String merchantDescrip;
    private String merchantHost; //le lien
    private String merchantLogo;
    private String callback; //
    private String serviceid;
    //cle secret
    private String sucretkey;
    private String accessKey;
}
