package com.codechallenge.callapp.configuration;

import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.io.IOException;

@Configuration
public class TemplateConfig implements WebMvcConfigurer {

    @Value("${template.src.path}")
    private String templateSrcPath;

    @Value("${view.default.content.type}")
    private String defaultContentType;

    @Value("${template.suffix.text}")
    private String templateSuffixText;

    @Value("${template.default.encode.type}")
    private String defaultEncodeType;

    @Bean
    public ViewResolver viewResolver() {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setCache(true);
        resolver.setSuffix(templateSuffixText);
        resolver.setContentType(defaultContentType);
        return resolver;
    }

    @Bean
    public FreeMarkerConfigurer freemarkerConfig() throws IOException, TemplateException {
        FreeMarkerConfigurationFactory factory = new FreeMarkerConfigurationFactory();
        factory.setTemplateLoaderPaths("classpath:templates", templateSrcPath);
        factory.setDefaultEncoding(defaultEncodeType);
        FreeMarkerConfigurer result = new FreeMarkerConfigurer();
        result.setConfiguration(factory.createConfiguration());
        return result;
    }
}
