package ma.m2t.chaabipay.entites;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("token")
@Entity
public class Token extends PaymentMethod {
  //  private Long tokenId;
  //  private String tokenResponse;
 //  private String tokenName;
 //   private String tokenEmail;
    ///////////////////////////operToken
    private String orderId;
    private String serviceId;
    private String organismId;
    private String customerMail;
    ////////////////////
    private String orderAmount;
    private String requestDate;
    private String expirationDate;
    private String checkSum;
    private String tokenStatus;
    /*###pour transaction service##*/
}