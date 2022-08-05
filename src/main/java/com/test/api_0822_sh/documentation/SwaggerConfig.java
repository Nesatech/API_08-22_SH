package com.test.api_0822_sh.documentation;

import org.springframework.boot.actuate.autoconfigure.endpoint.web.CorsEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementPortType;
import org.springframework.boot.actuate.endpoint.ExposableEndpoint;
import org.springframework.boot.actuate.endpoint.web.*;
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.ServletEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Swagger documentation of Api configuration
 */
@Configuration
public class SwaggerConfig {

    /**
     * Build the swagger api documentation
     *
     * @return Swagger api
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.test.api_0822_sh.controllers"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * Information about the API for documentation
     *
     * @return documentation information
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Test Api 0822 SH Documentation")
                .description("Documentation for the test Api")
                .termsOfServiceUrl("http://test-api.com")
                .contact(new Contact("Steve H", "http://test-api.com", "test-api@test-mail.com"))
                .build();
    }

    /**
     * Bean created to fix the start of bean documentationPluginsBootstrapper.
     * Get endpoints and check if they should be registered
     *
     * @param webEndpointsSupplier        webEndpointsSupplier
     * @param servletEndpointsSupplier    servletEndpointsSupplier
     * @param controllerEndpointsSupplier controllerEndpointsSupplier
     * @param endpointMediaTypes          endpointMediaTypes
     * @param corsProperties              corsProperties
     * @param webEndpointProperties       webEndpointProperties
     * @param environment                 environment
     * @return fixed WebMvcEndpointHandlerMapping
     */
    // Below methods added for fixing "ApplicationContextException: Failed to start bean 'documentationPluginsBootstrapper';" of SpringBoot/SpringFox
    @Bean
    public WebMvcEndpointHandlerMapping webEndpointServletHandlerMapping(WebEndpointsSupplier webEndpointsSupplier, ServletEndpointsSupplier servletEndpointsSupplier, ControllerEndpointsSupplier controllerEndpointsSupplier, EndpointMediaTypes endpointMediaTypes, CorsEndpointProperties corsProperties, WebEndpointProperties webEndpointProperties, Environment environment) {
        List<ExposableEndpoint<?>> allEndpoints = new ArrayList<>();
        Collection<ExposableWebEndpoint> webEndpoints = webEndpointsSupplier.getEndpoints();
        allEndpoints.addAll(webEndpoints);
        allEndpoints.addAll(servletEndpointsSupplier.getEndpoints());
        allEndpoints.addAll(controllerEndpointsSupplier.getEndpoints());
        String basePath = webEndpointProperties.getBasePath();
        EndpointMapping endpointMapping = new EndpointMapping(basePath);
        boolean shouldRegisterLinksMapping = this.shouldRegisterLinksMapping(webEndpointProperties, environment, basePath);
        return new WebMvcEndpointHandlerMapping(endpointMapping, webEndpoints, endpointMediaTypes, corsProperties.toCorsConfiguration(), new EndpointLinksResolver(allEndpoints, basePath), shouldRegisterLinksMapping, null);
    }

    /**
     * Check if a link should be mapped
     *
     * @param webEndpointProperties webEndpointProperties
     * @param environment           environment
     * @param basePath              basePath
     * @return true if link properties discovery is enabled && has text OR if environment of port type is different
     */
    private boolean shouldRegisterLinksMapping(WebEndpointProperties webEndpointProperties, Environment environment, String basePath) {
        return webEndpointProperties.getDiscovery().isEnabled() && (StringUtils.hasText(basePath) || ManagementPortType.get(environment).equals(ManagementPortType.DIFFERENT));
    }
}
