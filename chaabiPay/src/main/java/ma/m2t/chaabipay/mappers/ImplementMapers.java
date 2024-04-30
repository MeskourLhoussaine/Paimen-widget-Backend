package ma.m2t.chaabipay.mappers;

import ma.m2t.chaabipay.dtos.*;
import ma.m2t.chaabipay.entites.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class ImplementMapers {



    //
    public TransactionDTO fromTransaction(Transaction transaction) {
        TransactionDTO transactionDTO = new TransactionDTO();
        BeanUtils.copyProperties(transaction, transactionDTO);

        // Set PaymentMethod and Merchant IDs if available
        if (transaction.getPaymentMethod() != null) {
            transactionDTO.setPaymentMethodId(transaction.getPaymentMethod().getPaymentMethodId());
        }
        if (transaction.getMerchant() != null) {
            transactionDTO.setMerchantId(transaction.getMerchant().getMerchantId());
        }

        return transactionDTO;
    }

    public Transaction fromtransactionDTO(TransactionDTO transactionDTO){
        Transaction transaction=new Transaction();
        BeanUtils.copyProperties(transactionDTO,transaction);
        return  transaction;
    }
//mercha Transfomation between object JpA et object dto
public MerchantDTO fromMerchant(Merchant merchant){
    MerchantDTO merchantDTO = new MerchantDTO();
    BeanUtils.copyProperties(merchant, merchantDTO);
    return  merchantDTO;
}

    public Merchant fromMerchantDTO(MerchantDTO merchantDTO){
        Merchant merchant = new Merchant();
        BeanUtils.copyProperties(merchantDTO, merchant);
        return  merchant;
    }
//paimentMethode

    public PaimentMethodeDTO fromPaimentMethode(PaymentMethod paymentMethod){
        PaimentMethodeDTO paimentMethodeDTO = new PaimentMethodeDTO();
        BeanUtils.copyProperties(paymentMethod, paimentMethodeDTO);
        return  paimentMethodeDTO;
    }

    public PaymentMethod fromPaimentMethodeDTO(PaimentMethodeDTO paimentMethodeDTO){
        PaymentMethod paimentMethode = new PaymentMethod();
        BeanUtils.copyProperties(paimentMethodeDTO, paimentMethode);
        return  paimentMethode;
    }

    /*#############################les classe fils #######################*/
                                 /*###class Token #######*/
    public TokenDTO fromToken(Token token){
        TokenDTO tokenDTO = new TokenDTO();
        BeanUtils.copyProperties(token, tokenDTO);
        return  tokenDTO;
    }

    public Token fromTokenDTO(TokenDTO tokenDTO){
        Token token = new Token();
        BeanUtils.copyProperties(tokenDTO, token);
        return  token;
    }
                                  /*###class CreditCard #######*/

    public CreditCardDTO fromCreditCard(CreditCard creditCard){
        CreditCardDTO creditCardDTO = new CreditCardDTO();
        BeanUtils.copyProperties(creditCard, creditCardDTO);
        return  creditCardDTO;
    }

    public CreditCard fromCreditCardDTO(CreditCardDTO creditCardDTO){
        CreditCard creditCard = new CreditCard();
        BeanUtils.copyProperties(creditCardDTO, creditCard);
        return  creditCard;
    }
//Demande
    public DemandeDTO fromDemande(Demande demande){
        DemandeDTO demandeDTO =new DemandeDTO();
        BeanUtils.copyProperties(demande, demandeDTO);
        return demandeDTO;
    }

    public Demande fromDemandeDTO(DemandeDTO demandeDTO){
        Demande demande =new Demande();
        BeanUtils.copyProperties(demandeDTO, demande);
        return demande;
    }
}
