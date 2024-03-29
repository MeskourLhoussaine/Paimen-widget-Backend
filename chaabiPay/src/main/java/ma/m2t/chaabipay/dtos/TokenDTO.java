package ma.m2t.chaabipay.dtos;

import lombok.Data;

@Data
public class TokenDTO {
    private String tokenId;
    private String tokenResponse;
    private String tokenName;
    private String tokenEmail;
}
