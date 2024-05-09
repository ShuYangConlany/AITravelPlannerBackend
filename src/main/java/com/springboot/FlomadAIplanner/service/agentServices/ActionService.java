package com.springboot.FlomadAIplanner.service.agentServices;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.FlomadAIplanner.service.GeminiService;
import com.springboot.FlomadAIplanner.service.ThymeleafService;

@Service
public class ActionService {
    @Autowired
    private GeminiService geminiService;

    @Autowired
    private ThymeleafService thymeleafService;

    public String processMessages(List<String> messages) {
        if (messages == null || messages.isEmpty()) {
            return "No messages provided";
        }

        Map<String, Object> variables = new HashMap<>();
        variables.put("sessionMessages", String.join(" ", messages));
        variables.put("lastUserMessage", messages.get(messages.size() - 1));

        String geminiRequestText = thymeleafService.renderPlainText("ActionPrompt", variables);
        return geminiService.callGeminiApi(geminiRequestText);
    }
}
