package com.api.digital.security.securityconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

	@Bean
		public OpenAPI customOpenAPI(){
			final String securitySchemeName = "BearerAuth";
			
			return new OpenAPI()
					.info(new Info().title("API Digital Security").version("1,0")
							.description("API para monitoramento de dispositivos com foco em segurança digital"))
					.addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
					.components(new io.swagger.v3.oas.models.Components()
							.addSecuritySchemes(securitySchemeName,
									new SecurityScheme()
									.name(securitySchemeName)
									.type(SecurityScheme.Type.HTTP)
									.scheme("bearer")
									.bearerFormat("JWT")));
	}
	
}
