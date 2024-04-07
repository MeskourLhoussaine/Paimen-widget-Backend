package ma.m2t.chaabipay.entites;

import ma.m2t.chaabipay.entites.Token;
import org.springframework.stereotype.Component;

@Component
public class TokenFactory {

    public PaymentMethod createPaymentMethod(String tokenResponse, String tokenName, String tokenEmail) {
        Token token = new Token();
        token.setTokenResponse(tokenResponse);
        token.setTokenName(tokenName);
        token.setTokenEmail(tokenEmail);
        return token;
    }
}
