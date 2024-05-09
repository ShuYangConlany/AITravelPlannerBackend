package com.springboot.FlomadAIplanner.service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ThymeleafService {
    private final TemplateEngine templateEngine;

    @Autowired
    public ThymeleafService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String renderPlainText(String templateName, Map<String, Object> variables) {
        Context context = new Context();
        context.setVariables(variables);
        return templateEngine.process(templateName, context);
    }
}
