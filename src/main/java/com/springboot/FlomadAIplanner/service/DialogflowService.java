package com.springboot.FlomadAIplanner.service;

import com.google.cloud.dialogflow.cx.v3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.logging.Logger;
import com.google.protobuf.util.JsonFormat;


import com.google.cloud.dialogflow.cx.v3.DetectIntentRequest;
import com.google.cloud.dialogflow.cx.v3.DetectIntentResponse;
import com.google.cloud.dialogflow.cx.v3.QueryInput;
import com.google.cloud.dialogflow.cx.v3.SessionName;
import com.google.cloud.dialogflow.cx.v3.SessionsClient;
import com.google.cloud.dialogflow.cx.v3.SessionsSettings;
import com.google.cloud.dialogflow.cx.v3.TextInput;



// @Service
// public class DialogflowService {
//     private static final Logger logger = Logger.getLogger(DialogflowService.class.getName());

//     @Value("${dialogflow.projectId}")
//     private String projectId;

//     @Value("${dialogflow.locationId}")
//     private String locationId;

//     @Value("${dialogflow.agentId}")
//     private String agentId;

//     @Value("${dialogflow.sessionId}")
//     private String sessionId;

//     public String detectIntentText(String text) throws IOException {
//         String endpoint = locationId + "-dialogflow.googleapis.com:443";
//         logger.info("QQQQQQQQQQQQ333333333333333333333333333333333333333333333333333");
//         try (SessionsClient sessionsClient = SessionsClient.create(
            
//             SessionsSettings.newBuilder().setEndpoint(endpoint).build())) {
//             SessionName session = SessionName.of(projectId, locationId, agentId, sessionId);
//             TextInput.Builder textInput = TextInput.newBuilder().setText(text);
//             logger.info(text);

//             QueryInput queryInput = QueryInput.newBuilder()
//                 .setText(textInput)
//                 .setLanguageCode("en-US")
//                 .build();
//             logger.info("3");
//             DetectIntentRequest request = DetectIntentRequest.newBuilder()
//                     .setSession(session.toString())
//                     .setQueryInput(queryInput)
//                     .build();
//             String requestJson = JsonFormat.printer().print(request);
//             logger.info("Request JSON: " + requestJson);
//             logger.info("4");
//             logger.info(projectId);
//             logger.info(locationId);
//             logger.info(agentId);
//             logger.info(sessionId);
//             logger.info(endpoint);
//             logger.info("4");
//             logger.info("4");
//             DetectIntentResponse response = sessionsClient.detectIntent(request);
//             logger.info("5");
//             QueryResult result = response.getQueryResult();
//             StringBuilder responses = new StringBuilder();
//             logger.info("6");
//             for (ResponseMessage message : result.getResponseMessagesList()) {
//                 if (message.hasText()) {
//                     message.getText().getTextList().forEach(textSegment -> responses.append(textSegment).append("\n"));
//                 }
//             }
//             logger.info(responses.toString().trim());
//             return responses.toString().trim();
//         }catch (Exception e) {
//             logger.severe("Error communicating with Dialogflow: " + e.getMessage());
//             throw new IOException("Failed to communicate with Dialogflow API", e);
//         }
//     }
// }


@Service
public class DialogflowService {
    private static final Logger logger = Logger.getLogger(DialogflowService.class.getName());

    @Value("${dialogflow.projectId}")
    private String projectId;

    @Value("${dialogflow.locationId}")
    private String locationId;

    @Value("${dialogflow.agentId}")
    private String agentId;

    @Value("${dialogflow.sessionId}")
    private String sessionId;

    public String detectIntentText(String text) throws IOException {
        String endpoint = locationId + "-dialogflow.googleapis.com:443";
        logger.info("endpoint:"+endpoint);
        SessionsSettings sessionsSettings = SessionsSettings.newBuilder()
            .setEndpoint(endpoint)
            .build();

        try (SessionsClient sessionsClient = SessionsClient.create(sessionsSettings)) {
            // Use the SessionName class from Google Cloud Dialogflow CX v3
            SessionName session = SessionName.of(projectId, locationId, agentId, sessionId);
            logger.info("!!!!!!!!!!!!!!!");


            // Create the text input with the language code
            TextInput.Builder textInput = TextInput.newBuilder().setText(text);
            QueryInput queryInput = QueryInput.newBuilder()
                .setText(textInput)
                .setLanguageCode("en-US")
                .build();

            // Build the DetectIntentRequest with a new QueryParameters instance using the builder
            DetectIntentRequest request = DetectIntentRequest.newBuilder()
                .setSession(session.toString())
                .setQueryParams(QueryParameters.newBuilder().build())
                .setQueryInput(queryInput)
                .build();            

            // Log the request for debugging
            String requestJson = JsonFormat.printer().print(request);
            logger.info("Request JSON: " + requestJson);

            // Perform the detect intent request
            DetectIntentResponse response = sessionsClient.detectIntent(request);

            // Process the response
            QueryResult result = response.getQueryResult();
            StringBuilder responses = new StringBuilder();
            for (ResponseMessage message : result.getResponseMessagesList()) {
                if (message.hasText()) {
                    message.getText().getTextList().forEach(textSegment -> responses.append(textSegment).append("\n"));
                }
            }
            logger.info("Detected Intent Response: " + responses.toString().trim());
            return responses.toString().trim();
        } catch (Exception e) {
            logger.severe("Error communicating with Dialogflow: " + e.getMessage());
            throw new IOException("Failed to communicate with Dialogflow API", e);
        }
    }
}