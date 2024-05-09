package com.springboot.FlomadAIplanner.service.agentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.FlomadAIplanner.service.GeminiService;
import com.springboot.FlomadAIplanner.service.ThymeleafService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IntentService {
    @Autowired
    private GeminiService geminiService;

    @Autowired
    private ThymeleafService thymeleafService;

    public String processIntent(List<String> messages) {
        if (messages == null || messages.isEmpty()) {
            return "No messages provided";
        }
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("sessionMessages", String.join(" ", messages));
        variables.put("lastUserMessage", messages.get(messages.size() - 1));

        String geminiRequestText = thymeleafService.renderPlainText("IntentPrompt", variables);
        return geminiService.callGeminiApi(geminiRequestText);
    }
}
