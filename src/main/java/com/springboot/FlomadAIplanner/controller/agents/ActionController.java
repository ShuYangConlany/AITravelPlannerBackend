package com.springboot.FlomadAIplanner.controller.agents;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.springboot.FlomadAIplanner.service.agentServices.ActionService;

@Controller
// @RequestMapping("/travel")
public class ActionController {

    @Autowired
    private ActionService ActionService;

    @PostMapping("/action")
    public ResponseEntity<String> planTravel(@RequestBody List<String> messages) {
        String result = ActionService.processMessages(messages);
        if (result.equals("No messages provided")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }
}