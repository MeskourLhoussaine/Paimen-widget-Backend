package ma.m2t.chaabipay.controller;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paypal")
public class PayPalController {

    @Value("${paypal.client.id}")
    private String clientId;

    @Value("${paypal.client.secret}")
    private String clientSecret;

    @Value("${paypal.mode}")
    private String mode;

    @PostMapping("/create-payment")
    public String createPayment() {
        try {
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

            // Create payment
            Payment createdPayment = payment.create(apiContext);

            // Extract the approval URL from the payment object
            for (Links links : createdPayment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    return links.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/execute-payment")
    public String executePayment(@RequestParam("paymentId") String paymentId, @RequestParam("payerId") String payerId) {
        try {
            // Configure PayPal environment
            APIContext apiContext = new APIContext(clientId, clientSecret, mode);

            // Execute payment
            Payment payment = new Payment();
            payment.setId(paymentId);

            // Execute payment with payer ID
            PaymentExecution paymentExecution = new PaymentExecution();
            paymentExecution.setPayerId(payerId);

            Payment executedPayment = payment.execute(apiContext, paymentExecution);

            // Check if payment state is approved
            if (executedPayment.getState().equals("approved")) {
                return "Payment successful";
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return "Payment failed";
    }
}
