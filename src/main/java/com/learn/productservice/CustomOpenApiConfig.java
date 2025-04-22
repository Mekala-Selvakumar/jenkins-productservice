package com.learn.productservice;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@Configuration
@OpenAPIDefinition(info = @Info(

		title = "Product Service API", 
		version = "v1.0", 
		description = "This is custom description for Product Service API", 
		termsOfService = "http://example.com/terms/", 
		contact = @Contact(name = "Person XYZ", 
		                    url = "http://example.com/support",
		                    email = "support@example.com"),

		license = @License(
				name = "Apache",
		        url = "http://www.apache.org/licenses/LICENSE-2.0")

))

public class CustomOpenApiConfig {

}



