package com.springboot.FlomadAIplanner.controller;

// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RestController;

// import com.springboot.FlomadAIplanner.DialogflowWebhookService.WbhookFlightJumpPage;

// import org.springframework.beans.factory.annotation.Autowired;

// @RestController
// public class WebhookController {

//     private final WbhookFlightJumpPage wbhookFlightJumpPage;

//     @Autowired
//     public WebhookController(WbhookFlightJumpPage wbhookFlightJumpPage) {
//         this.wbhookFlightJumpPage = wbhookFlightJumpPage;
//     }

//     @PostMapping("/webhook")
//     public String handleWebhook(@RequestBody String request) {
//         try {
//             return wbhookFlightJumpPage.processRequest(request);
//         } catch (Exception e) {
//             return "Error handling request: " + e.getMessage();
//         }
//     }
// }



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.springboot.FlomadAIplanner.service.WebhookFlightJumpPageService;
import java.util.logging.Logger;

@RestController
public class WebhookController {
    private final WebhookFlightJumpPageService service;
    private static final Logger logger = Logger.getLogger(WebhookController.class.getName());

    @Autowired
    public WebhookController(WebhookFlightJumpPageService service) {
        this.service = service;
    }

    @PostMapping("/webhook")
    public String handleWebhook(@RequestBody String request) {
        return service.processRequest(request);
    }
}
