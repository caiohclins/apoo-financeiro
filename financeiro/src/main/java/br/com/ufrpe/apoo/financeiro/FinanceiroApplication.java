package br.com.ufrpe.apoo.financeiro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@org.springframework.cloud.openfeign.EnableFeignClients
public class FinanceiroApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinanceiroApplication.class, args);
	}

}
