package ma.m2t.chaabipay.dtos;

import lombok.Data;
import ma.m2t.chaabipay.entites.Merchant;

@Data
public class PaimentMethodeDTO {

    private Long paymentMethodId;
    private String name;
    private String description;
    private String iconUrl;
    private boolean status;


}
