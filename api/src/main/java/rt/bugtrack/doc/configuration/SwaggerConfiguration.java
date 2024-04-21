package rt.bugtrack.doc.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfiguration {
    final String securitySchemeName = "bearerAuth";
    @Bean
    public OpenAPI springOpenAPI() {
        return new OpenAPI()
                .info(
                    new Info().title("Bugtrack API")
                        .description("Bugtracking system API")
                        .version("v0.0.1")
                )
                .addSecurityItem(
                    new SecurityRequirement()
                        .addList(securitySchemeName)
                )
                .components(
                    new Components()
                        .addSecuritySchemes(
                            securitySchemeName,
                            new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                        )
                );
    }
}

