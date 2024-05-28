package ma.m2t.chaabipay;

import ma.m2t.chaabipay.controller.AuthController;
import ma.m2t.chaabipay.services.implement.MerchantImplement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"ma.m2t.chaabipay.entites", "ma.m2t.chaabipay.entites.Token"})

public class ChaabiPayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChaabiPayApplication.class, args);
        // Créez une instance de MerchantImplement après avoir initialisé Spring Boot
        MerchantImplement merchantImplement=new MerchantImplement();

        String merchantId = "4";
        String orderId = "111";
        double amount = 1000.0;
        String currency = "MAD";
        String secretKey = "5AM2MwyO0TAElCkf+HkI4uoJ8XimGWdYH9OwUpjj7h8=";

        // Appel de la méthode generateHmac avec les paramètres appropriés
        String hmac = merchantImplement.generateHmac(merchantId, orderId, amount, currency, secretKey);
        System.out.println("Generated HMAC: " + hmac);
        AuthController auth=new AuthController();
/*
AuthController authe =new AuthController();
        System.out.println("Generated Password: " + authe.generateRandomPassword());*/
    }

}
