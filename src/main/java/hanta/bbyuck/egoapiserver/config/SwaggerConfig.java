package hanta.bbyuck.egoapiserver.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @SuppressWarnings("deprecation")
    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "ego api server",
                "eSports 매칭 플랫폼 ego API Doc. --> 현재 이용 가능한 클라이언트 버전 : v1.00, v1.01, v1.02",
                "0.0.1.Beta",
                "",
                "Bbyuck - HYUK",
                "The Apache License, Version 2.0",
                "https://httpd.apache.org/docs/2.4/license.html"
        );
        return apiInfo;
    }


    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }
}
