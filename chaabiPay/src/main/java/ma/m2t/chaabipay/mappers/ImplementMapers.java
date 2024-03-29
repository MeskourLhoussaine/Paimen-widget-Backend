package ma.m2t.chaabipay.mappers;

import ma.m2t.chaabipay.dtos.MerchantDTO;
import ma.m2t.chaabipay.dtos.PaimentMethodeDTO;
import ma.m2t.chaabipay.dtos.TransactionDTO;
import ma.m2t.chaabipay.entites.Merchant;
import ma.m2t.chaabipay.entites.PaymentMethod;
import ma.m2t.chaabipay.entites.Transaction;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class ImplementMapers {



    //
    public TransactionDTO fromTransaction(Transaction transaction){
        TransactionDTO transactionDTO=new TransactionDTO();
        BeanUtils.copyProperties(transaction,transactionDTO);
        return  transactionDTO;
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

    public PaymentMethod PaimentMethodeDTO(PaimentMethodeDTO paimentMethodeDTO){
        PaymentMethod paimentMethode = new PaymentMethod();
        BeanUtils.copyProperties(paimentMethodeDTO, paimentMethode);
        return  paimentMethode;
    }

}
