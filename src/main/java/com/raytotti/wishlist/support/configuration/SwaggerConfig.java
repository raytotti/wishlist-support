package com.raytotti.wishlist.support.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi wishlistSupportApi() {
        return GroupedOpenApi.builder()
                .group("wishlist-support")
                .pathsToMatch("/api/v1/**")
                .build();
    }

    @Bean
    public GroupedOpenApi wishlistSupportClientApi() {
        return GroupedOpenApi.builder()
                .group("wishlist-support-clients")
                .pathsToMatch("/api/v1/clients/**")
                .build();
    }

    @Bean
    public GroupedOpenApi wishlistSupportProductsApi() {
        return GroupedOpenApi.builder()
                .group("wishlist-support-products")
                .pathsToMatch("/api/v1/products/**")
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI(@Value("${spring-doc.version}") String appVersion) {
        return new OpenAPI()
                .info(new Info()
                        .title("Wishlist Support API")
                        .description("This microservice is responsible for maintaining the necessary information for the functioning of the [Wishlist API microservice](https://github.com/raytotti/wishlist)")
                        .version(appVersion)
                        .license(new License().name("Ray Toti Felix de Araujo").url("https://github.com/raytotti/wishlist-support")));
    }
}
