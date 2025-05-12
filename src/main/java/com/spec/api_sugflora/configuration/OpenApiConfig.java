package com.spec.api_sugflora.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.License;

@Configuration
@EnableWebMvc
@OpenAPIDefinition(info = @io.swagger.v3.oas.annotations.info.Info(title = "API Documentation", version = "1.0.0", description = "API documentation for the project", contact = @Contact(name = "Support", email = "support@example.com"), license = @License(name = "Apache 2.0", url = "http://springdoc.org")))
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Documentation")
                        .version("1.0.0")
                        .description("API documentation for the project"))
                .addSecurityItem(new SecurityRequirement().addList("token"))
                .components(new Components().addSecuritySchemes("token",
                        new SecurityScheme()
                                .name("token")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));

    }

    @Bean
    public OpenApiCustomizer openApiCustomizer() {
        return openApi -> {
            Paths filteredPaths = new Paths();
            openApi.getPaths().forEach((path, pathItem) -> {
                if (path.contains("/api")) { // Filtra apenas endpoints que começam com "/api"
                    filteredPaths.addPathItem(path, pathItem);
                }
            });
            openApi.setPaths(filteredPaths);
        };
    }
}