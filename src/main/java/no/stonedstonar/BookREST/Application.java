package no.stonedstonar.BookREST;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public Docket swaggerConfiguration(){
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.paths(PathSelectors.ant("/books/*"))
				.apis(RequestHandlerSelectors.basePackage("no.stonedstonar"))
				.build()
				.apiInfo(apiDetails());
	}


	private ApiInfo apiDetails(){
		return new ApiInfo(
				"Book example API",
				"An example of a application with swagger",
				"1.0",
				"Luli",
				new Contact("Luli", "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "luli@lul.com"),
				"API License",
				"lul",
				Collections.emptyList()
		);
	}
}
