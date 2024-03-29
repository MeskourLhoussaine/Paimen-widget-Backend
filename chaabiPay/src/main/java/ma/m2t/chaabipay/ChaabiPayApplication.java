package ma.m2t.chaabipay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"ma.m2t.chaabipay.entites", "ma.m2t.chaabipay.entites.Token"})

public class ChaabiPayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChaabiPayApplication.class, args);
    }

}
