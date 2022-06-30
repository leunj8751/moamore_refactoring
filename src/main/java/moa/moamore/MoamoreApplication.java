package moa.moamore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MoamoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoamoreApplication.class, args);
    }

}
