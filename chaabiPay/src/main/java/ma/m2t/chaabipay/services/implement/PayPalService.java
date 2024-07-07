package ma.m2t.chaabipay.services.implement;
/*import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PayPalService {

    @Value("${paypal.client.id}")
    private String clientId;

    @Value("${paypal.client.secret}")
    private String clientSecret;

    @Value("${paypal.mode}")
    private String mode;

    public Payment createPayment() throws PayPalRESTException {
        // Configure PayPal environment
        APIContext apiContext = new APIContext(clientId, clientSecret, mode);

        // Create a payment object
        Payment payment = new Payment();
        // Set payment details (amount, currency, etc.)
        // Example:
        /*
        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal("100.00");

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        // Set other transaction details as needed

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        payment.setIntent("sale");
        payment.setTransactions(transactions);
        */

       /* // Create payment
        return payment.create(apiContext);
    }

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        // Configure PayPal environment
        APIContext apiContext = new APIContext(clientId, clientSecret, mode);

        // Execute payment
        Payment payment = new Payment();
        payment.setId(paymentId);

        // Execute payment with payer ID
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        return payment.execute(apiContext, paymentExecution);
    }
}
*/
