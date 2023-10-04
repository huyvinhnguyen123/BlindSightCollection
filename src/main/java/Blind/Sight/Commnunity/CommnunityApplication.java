package Blind.Sight.Commnunity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "Blind.Sight.Community")
public class CommnunityApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommnunityApplication.class, args);
	}

}
