package com.springboot.FlomadAIplanner.DialogflowWebhookService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;


////////////////////////////////////////////
/**
 * @class
 * @description Since the dialogflow CX can only communicate with a function that have been deployed on the internet, all code here 
 * transfered to webhook file in root path
 */
//////////////////////////////////////////
@RestController
public class SkipFlightReq {
    private static final Logger logger = LoggerFactory.getLogger(SkipFlightReq.class);

    @PostMapping("/SkipFlightReq")
    public void handleDialogflowWebhook(@RequestBody String requestBody) {
        logger.info("Received Dialogflow request: {}", requestBody);
    }
}