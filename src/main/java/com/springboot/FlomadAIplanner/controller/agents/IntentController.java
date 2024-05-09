package com.springboot.FlomadAIplanner.controller.agents;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.springboot.FlomadAIplanner.service.agentServices.IntentService;

@Controller
public class IntentController {

    @Autowired
    private IntentService intentService;

    @PostMapping("/intent")
    public ResponseEntity<String> planTravel(@RequestBody List<String> messages) {
        String result = intentService.processIntent(messages);
        if (result.equals("No messages provided")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }
}
