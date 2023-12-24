package com.digsol.turnomed.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spi.service.contexts.SecurityContextBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Profile({"localdev", "dev"})
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("TurnoMed server")
                .description("API") // API description in Markdown.
                .license("")
                .version("0.1")
                .build();
    }

    @Bean
    public Docket customImplementation() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.digsol.turnomed"))
                .build()
                .apiInfo(apiInfo())
                .securitySchemes(Collections.singletonList(new ApiKey("x-auth-token", "x-auth-token", "м ")))
                .securityContexts(Collections.singletonList(securityContext()))
                ;
    }

    public SecurityContext securityContext() {
        return SecurityContext.builder().forPaths(s -> true).forHttpMethods(m -> true).securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope(
                "global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("x-auth-token",
                authorizationScopes));
    }

}
