package com.generation.lojagames.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springBlogPessoalOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Loja de Games")
                        .description("Projeto Loja de Games - Edviges de Lima")
                        .version("0.0.1")
                        .license(new License()
                                .name("Edviges de Lima")
                                .url("https://github.com/edvigeslima"))
                        .contact(new Contact()
                                .name("edvigeslima")
                                .email("edvigeslima@example.com")
                                .url("https://github.com/edvigeslima")))
                .externalDocs(new ExternalDocumentation()
                        .description("GitHub").url("https://github.com/edvigeslima"));
    }

    @Bean
    public OpenApiCustomizer customerGlobalHeaderOpenApiCustomizer(){
        return openApi -> {
            openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {
                ApiResponses apiResponses = operation.getResponses();

                apiResponses.addApiResponse("200", createApiResponse("Sucesso!"));
                apiResponses.addApiResponse("201", createApiResponse("Objeto Persistido!"));
                apiResponses.addApiResponse("204", createApiResponse("Objeto deletado!"));
                apiResponses.addApiResponse("400", createApiResponse("Erro na Requisição!"));
                apiResponses.addApiResponse("401", createApiResponse("Acesso não autorizado!"));
                apiResponses.addApiResponse("404", createApiResponse("Objeto nao encontrado!"));
                apiResponses.addApiResponse("500", createApiResponse("Erro na aplicação!"));
            }));
        };
    }

    private ApiResponse createApiResponse(String message) {
        return new ApiResponse().description(message);
    }
}