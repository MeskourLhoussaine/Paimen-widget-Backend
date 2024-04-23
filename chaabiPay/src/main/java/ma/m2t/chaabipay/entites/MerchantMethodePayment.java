package ma.m2t.chaabipay.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "merchant_paymentmethod")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MerchantMethodePayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "merchantId")
    private Merchant merchant;

    @ManyToOne
    @JoinColumn(name = "paymentMethodId")
    private PaymentMethod paymentMethod;

    private boolean status;

    // Getters and setters
}
