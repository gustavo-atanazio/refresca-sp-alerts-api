package com.refresca_sp.infra.springdoc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
      .info(new Info()
        .title("API de Alertas - Refresca SP")
        .version("1.0")
        .description("API REST de gerenciamento de alertas de ondas de calor. Faz parte da Global Solution 2026.2 da FIAP - Arquitetura Orientada a Serviços (SOA).")
      )
    ;
  }
}