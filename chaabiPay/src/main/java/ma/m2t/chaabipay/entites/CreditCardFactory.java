package ma.m2t.chaabipay.entites;

import ma.m2t.chaabipay.entites.CreditCard;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CreditCardFactory {

    public PaymentMethod createPaymentMethod(String cardNumber, String cardHolderName, Date expirationDate, String cvv) {
        CreditCard creditCard = new CreditCard();
        creditCard.setNumberCard(cardNumber);
        creditCard.setTitulairName(cardHolderName);
        creditCard.setDateExper(expirationDate);
        creditCard.setCvv(cvv);
        return creditCard;
    }
}
