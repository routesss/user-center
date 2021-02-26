package com.sola.usercenter.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.any()).build().globalOperationParameters(getParameterList());
    }

    private ApiInfo apiInfo() {
        // springfox.documentation.service.Contact
        Contact contact = new Contact("团队名", "www.my.com", "my@my.com");
        return new ApiInfoBuilder().title("文档标题").description("文档描述").contact(contact) // 联系方式
            .version("1.1.0") // 版本
            .build();
    }

    /**
     * 添加token
     * 
     * @return
     */
    private List<Parameter> getParameterList() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        ArrayList<Parameter> pars = new ArrayList<>();
        tokenPar.name("X-Token").description("令牌").modelRef(new ModelRef("string")).parameterType("header")
            .required(false);// token非必填
        pars.add(tokenPar.build());
        return pars;
    }
}
