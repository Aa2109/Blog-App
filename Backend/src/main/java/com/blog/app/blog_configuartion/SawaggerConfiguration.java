package com.blog.app.blog_configuartion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SawaggerConfiguration {

	  @Bean
	  OpenAPI springShopOpenAPI() {
	      return new OpenAPI()
	              .info(new Info().title("Blog App API")
	              .description("Blog application for learning backend application")
	              .version("v0.0.1")
	              .license(new License().name("Aashif").url("http://localhost:8080/api/auth/register")))
	              .externalDocs(new ExternalDocumentation()
	              .description("SpringShop Wiki Documentation")
	              .url("https://springshop.wiki.github.org/docs"));
	  }

}
