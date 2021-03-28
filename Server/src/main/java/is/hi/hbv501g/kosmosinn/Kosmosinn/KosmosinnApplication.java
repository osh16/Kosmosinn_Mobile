package is.hi.hbv501g.kosmosinn.Kosmosinn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class KosmosinnApplication{
	public static void main(String[] args) { SpringApplication.run(KosmosinnApplication.class, args); }
}