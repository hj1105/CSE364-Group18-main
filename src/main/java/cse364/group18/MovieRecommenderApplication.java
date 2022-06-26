package cse364.group18;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class MovieRecommenderApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(MovieRecommenderApplication.class, args);
	}
}