package com.vitor.springcloudmysql;

import java.util.stream.LongStream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.vitor.springcloudmysql.model.Contact;
import com.vitor.springcloudmysql.repository.ContactRepository;

@SpringBootApplication
public class SpringcloudmysqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudmysqlApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init(ContactRepository repository) {
		return args -> {
			repository.deleteAll();
			LongStream.range(1, 11)
					.mapToObj(i -> {
						Contact c = new Contact();
						c.setName("Contact " + i);
						c.setEmail("contact" + i + "@email.com");
						c.setCpf("(111) 111-1111" + i);
						c.setBirthday("07/01/2001" + i);
						return c;
					})
					.map(v -> repository.save(v))
					.forEach(System.out::println);
		};
	}
}
