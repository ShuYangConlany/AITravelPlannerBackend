package com.springboot.FlomadAIplanner.service;

import com.google.cloud.dialogflow.cx.v3.*;
import com.google.cloud.dialogflow.cx.v3.ResponseMessage.Text;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import com.google.protobuf.util.JsonFormat;



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

    public String processInput(String userInput) throws Exception {
        String projectId = "flomadaiplanner";
        String locationId = "us-central1";
        String agentId = "7fb1e868-d836-46d8-b57a-91fd6c0e6d34";
        String sessionId = "unique-session-id2";
        String endpoint = "us-central1-dialogflow.googleapis.com:443";

        SessionsSettings.Builder sessionsSettingsBuilder = SessionsSettings.newBuilder();
        sessionsSettingsBuilder.setEndpoint(endpoint);

        try (SessionsClient sessionsClient = SessionsClient.create(sessionsSettingsBuilder.build())) {
            String sessionPath = SessionName.of(projectId, locationId, agentId, sessionId).toString();
            TextInput.Builder textInputBuilder = TextInput.newBuilder().setText(userInput);

            QueryInput queryInput = QueryInput.newBuilder().setText(textInputBuilder).setLanguageCode("en-US").build();
            DetectIntentRequest detectIntentRequest = DetectIntentRequest.newBuilder()
                    .setSession(sessionPath)
                    .setQueryInput(queryInput)
                    .build();
            logger.info(detectIntentRequest.toString());

            DetectIntentResponse response = sessionsClient.detectIntent(detectIntentRequest);
            logger.info("response:"+response.toString());

            QueryResult queryResult = response.getQueryResult();

            StringBuilder responseTextBuilder = new StringBuilder();
            List<ResponseMessage> messages = queryResult.getResponseMessagesList();
            for (ResponseMessage message : messages) {
                if (message.hasText()) {
                    Text textMessage = message.getText();
                    responseTextBuilder.append(String.join("\n", textMessage.getTextList()));
                }
            }
            logger.info("responseTextBuilder:"+responseTextBuilder.toString());
            return responseTextBuilder.toString();
        }
    }
}