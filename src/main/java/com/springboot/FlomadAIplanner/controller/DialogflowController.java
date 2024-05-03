package com.springboot.FlomadAIplanner.controller;
// import java.io.BufferedWriter;

// import com.google.cloud.functions.HttpFunction;
// import com.google.cloud.functions.HttpRequest;
// import com.google.cloud.functions.HttpResponse;

// import java.io.IOException;

// import com.google.cloud.dialogflow.cx.v3.DetectIntentRequest;
// import com.google.cloud.dialogflow.cx.v3.DetectIntentResponse;
// import com.google.cloud.dialogflow.cx.v3.QueryInput;
// import com.google.cloud.dialogflow.cx.v3.QueryResult;
// import com.google.cloud.dialogflow.cx.v3.ResponseMessage;
// import com.google.cloud.dialogflow.cx.v3.SessionName;
// import com.google.cloud.dialogflow.cx.v3.SessionsClient;
// import com.google.cloud.dialogflow.cx.v3.TextInput;
// import com.google.cloud.dialogflow.cx.v3.SessionsSettings;
// import java.util.logging.Logger;
// import java.util.stream.Collectors;

// import org.springframework.beans.factory.annotation.Autowired;
import com.springboot.FlomadAIplanner.service.DialogflowService;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;



////////////////////////////////////////////
/**
 * @class
 * @description Send request to dialogflow CX then receive response.
 */
//////////////////////////////////////////
// public class DialogflowController implements HttpFunction {
//     private static final Logger logger = Logger.getLogger(DialogflowController.class.getName());
//     private final DialogflowService dialogflowService;
//     public DialogflowController(DialogflowService dialogflowService) {
//         this.dialogflowService = dialogflowService;
//     }
    
//     @Override
//     public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
//         logger.info("1");
//         httpResponse.appendHeader("Access-Control-Allow-Origin", "*");
//         httpResponse.appendHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
//         httpResponse.appendHeader("Access-Control-Allow-Headers", "Content-Type");
        
//         if (httpRequest.getMethod().equals("OPTIONS")) {
//             httpResponse.setStatusCode(204);
//             return;
//         }
//         // Dialogflow CX params
//         String projectId = "flomadaiplanner";
//         String locationId = "us-central1";
//         String agentId = "7fb1e868-d836-46d8-b57a-91fd6c0e6d34";
//         String sessionId = "unique-session-id";
//         String endpoint = "us-central1-dialogflow.googleapis.com:443";
//         SessionsSettings.Builder sessionsSettingsBuilder = SessionsSettings.newBuilder();
//         sessionsSettingsBuilder.setEndpoint(endpoint);
//         String userInput = httpRequest.getReader().lines().collect(Collectors.joining());
//         // init SessionsClient
//         try (SessionsClient sessionsClient = SessionsClient.create(sessionsSettingsBuilder.build())) {
//             //set session path
//             String sessionPath = SessionName.of(projectId, locationId, agentId, sessionId).toString();

//             // create text input
//             TextInput.Builder textInputBuilder = TextInput.newBuilder().setText(userInput);
//             QueryInput queryInput = QueryInput.newBuilder().setText(textInputBuilder).setLanguageCode("en-US").build();
//             DetectIntentRequest detectIntentRequest = DetectIntentRequest.newBuilder()
//                     .setSession(sessionPath)
//                     .setQueryInput(queryInput)
//                     .build();



//             DetectIntentResponse response = sessionsClient.detectIntent(detectIntentRequest);
//             QueryResult queryResult = response.getQueryResult();
//             StringBuilder fulfillmentTextBuilder = new StringBuilder();
//             for (ResponseMessage message : queryResult.getResponseMessagesList()) {
//                 if (message.hasText()) {
//                     for (String textSegment : message.getText().getTextList()) {
//                         fulfillmentTextBuilder.append(textSegment).append("\n");
//                     }
//                 }
//             }
//             writeResponse(httpResponse, fulfillmentTextBuilder.toString());
//         }

        
//     }
//     private void writeResponse(HttpResponse httpResponse, String responseText) throws IOException {
//         httpResponse.setContentType("application/json; charset=UTF-8");
//         BufferedWriter writer = httpResponse.getWriter();
//         // return as json type
//         String jsonResponse = "{\"message\": \"" + responseText.trim().replaceAll("\n", "\\n") + "\"}";
//         writer.write(jsonResponse);
//     }

//     @PostMapping("/detect-intent")
//     public String detectIntent(@RequestBody String userInput) {
//         try {
//             return dialogflowService.detectIntentText(userInput);
//         } catch (Exception e) {
//             return "Error processing the request: " + e.getMessage();
//         }
//     }
// }

// @CrossOrigin(
//     origins = "http://localhost:4200", 
//     allowedHeaders = "*", 
//     methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS}, 
//     allowCredentials = "true"
// )



// @CrossOrigin(origins = "*", allowCredentials = "true")
// @RestController
// @RequestMapping("/dialogflow")
// // @CrossOrigin(origins = "http://localhost:4200")
// public class DialogflowController {

//     private final DialogflowService dialogflowService;

//     public DialogflowController(DialogflowService dialogflowService) {
//         this.dialogflowService = dialogflowService;
//     }

//     // @PostMapping("/detect_intent")
//     // public String detectIntent(@RequestBody String userInput) {
//     //     try {
//     //         return dialogflowService.detectIntentText(userInput);
//     //     } catch (Exception e) {
//     //         return "Error processing the request: " + e.getMessage();
//     //     }
//     // }
//     @PostMapping("/detect_intent")
//     public String detectIntent(@RequestBody String userInput, HttpServletResponse response) {
//         setCorsHeaders(response);
//         try {
//             return dialogflowService.detectIntentText(userInput);
//         } catch (Exception e) {
//             return "Error processing the request: " + e.getMessage();
//         }
//     }

//     private void setCorsHeaders(HttpServletResponse response) {
//         response.setHeader("Access-Control-Allow-Origin", "*");
//         response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
//         response.setHeader("Access-Control-Allow-Headers", "Content-Type");

//         if ("OPTIONS".equalsIgnoreCase(((HttpServletRequest) response).getMethod())) {
//             response.setStatus(HttpServletResponse.SC_NO_CONTENT);
//         }
//     }
// }


@RestController
@RequestMapping("/dialogflow")
public class DialogflowController {

    private final DialogflowService dialogflowService;

    public DialogflowController(DialogflowService dialogflowService) {
        this.dialogflowService = dialogflowService;
    }

    @PostMapping("/detect_intent")
    public String detectIntent(@RequestBody String userInput, HttpServletResponse response) {
        setCorsHeaders(response);
        try {
            return dialogflowService.detectIntentText(userInput);
        } catch (Exception e) {
            return "Error processing the request: " + e.getMessage();
        }
    }

    private void setCorsHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }

    @RequestMapping(value = "/**", method = RequestMethod.OPTIONS)
    public void handleOptions(HttpServletResponse response) {
        setCorsHeaders(response);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}