package com.web2.movelcontrol.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // Indica que esta é uma classe de configuração do Spring
public class OpenApiConfig {
	
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("MovelControl API") // Título da sua API
						.version("v1") // Versão da sua API
						.description("API para gerenciamento de orçamentos e pedidos para marcenaria.") // Descrição
						.termsOfService("http://movelcontrol.com.br/termos_de_servico") // Seu link para termos de serviço (fictício por enquanto)
						.license(new License()
								.name("Apache 2.0") // Tipo de licença
								.url("http://springdoc.org") // Link para a licença (pode ser o link oficial da licença)
						)
				);
	}
}