package com.springboot.FlomadAIplanner;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class ThymeleafConfig {
    
    @Bean
    public TemplateEngine customTemplateEngine() {
        TemplateEngine engine = new TemplateEngine();
        engine.addTemplateResolver(textTemplateResolver());
        return engine;
    }

    @Bean
    public ClassLoaderTemplateResolver textTemplateResolver() {
        ClassLoaderTemplateResolver textResolver = new ClassLoaderTemplateResolver();
        textResolver.setPrefix("templates/"); 
        textResolver.setSuffix(".txt");      
        textResolver.setTemplateMode("TEXT"); 
        textResolver.setCharacterEncoding("UTF-8");
        textResolver.setOrder(1);             
        textResolver.setCheckExistence(true); 
        return textResolver;
    }
}
