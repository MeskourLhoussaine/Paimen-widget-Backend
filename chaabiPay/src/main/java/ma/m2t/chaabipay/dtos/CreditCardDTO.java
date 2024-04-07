package ma.m2t.chaabipay.dtos;

import lombok.Data;

import java.util.Date;
@Data
public class CreditCardDTO {
    private Long cartId;
    private String numberCard;
    private String titulairName;
    private Date dateExper;
    private String cvv;
}
