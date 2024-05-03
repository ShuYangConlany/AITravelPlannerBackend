package com.springboot.FlomadAIplanner.service;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import java.util.Map;
import java.util.HashMap;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.cloud.dialogflow.cx.v3beta1.WebhookResponse;
import com.google.cloud.dialogflow.cx.v3beta1.WebhookResponse.FulfillmentResponse;
import com.google.protobuf.Value;
import com.google.cloud.dialogflow.cx.v3beta1.ResponseMessage;
import com.google.protobuf.util.JsonFormat;
import com.springboot.FlomadAIplanner.controller.FlightOfferController;

@Service
public class WebhookFlightJumpPageService {
    private static final Logger logger = Logger.getLogger(WebhookFlightJumpPageService.class.getName());
    @Autowired
    private FlightOfferController flightOfferController;

    public String processRequest(String requestBody) {
        logger.info("Received request: " + requestBody);
        JsonObject json = JsonParser.parseString(requestBody).getAsJsonObject();
        String currentPage = json.getAsJsonObject("pageInfo").get("currentPage").getAsString();
        Map<String, Value> parameters = extractParameters(json);

        WebhookResponse webhookResponse = handleWebhookRequest(parameters, currentPage);
        try {
            return JsonFormat.printer().print(webhookResponse);
        } catch (Exception e) {
            logger.severe("Failed to serialize response: " + e.getMessage());
            return "{}";
        }
    }

    private Map<String, Value> extractParameters(JsonObject json) {
        Map<String, Value> parameters = new HashMap<>();
        JsonObject sessionInfo = json.getAsJsonObject("sessionInfo");
        if (sessionInfo != null && sessionInfo.has("parameters")) {
            JsonObject sessionParametersJson = sessionInfo.getAsJsonObject("parameters");
            sessionParametersJson.entrySet().forEach(entry -> {
                String key = entry.getKey();
                JsonElement value = entry.getValue();
                if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isString()) {
                    parameters.put(key, Value.newBuilder().setStringValue(value.getAsString()).build());
                }
            });
        }
        return parameters;
    }

    public WebhookResponse handleWebhookRequest(Map<String, Value> parameters, String currentPage) {
        boolean shouldGoToPageFlightOffers = false; 
        boolean shouldGoToPageLocationReq = false;
        boolean shouldGoToPageDestinationReq = false;
        boolean shouldGoToPageDepTimeReq = false;
        boolean shouldGoToPageReturnTimeReq = false;
        boolean shouldGoToPageReturnAccompanyNumReq = false;
        boolean shouldGoToPageB = false;
        String header = "projects/flomadaiplanner/locations/us-central1/agents/7fb1e868-d836-46d8-b57a-91fd6c0e6d34/flows/00000000-0000-0000-0000-000000000000/pages/";
        String PageFlightOffers=header+"6e7a3853-4000-4ba4-8ff9-f81f0392e280";
        String PageLocationReq= header+"4076f3ef-73dd-425a-9b27-817fb7e77629";
        String PageDestinationReq= header+"edeb0351-814c-44e1-ad47-ca3dc9084c2c";
        String PageDepTimeReq= header+"f98078a1-7e34-4ee9-9618-b9c5727e9acb";
        String PageReturnTimeReq= header+"0820e90e-472b-4778-9655-575c400e095a";
        String PageReturnAccompanyNumReq= header+"4650c5b5-44d9-4e33-a1cc-37c7832027de";
        String PageGetFlight=header+"958c6bea-cadb-4ed9-ae37-a30b61bea928";



        logger.info("Alfffffffff");
        if (parameters.containsKey("isroundtrip")) {
            
            Value isRoundTrip = parameters.get("isroundtrip");
        } else {
            shouldGoToPageFlightOffers = true;
        }
        if (parameters.containsKey("departure_city")) {
            Value departure_city = parameters.get("departure_city");
        } else{
            shouldGoToPageLocationReq = true;
        }
        if (parameters.containsKey("destination_city")) {
            Value destination_city = parameters.get("destination_city");
        } else {
            shouldGoToPageDestinationReq = true;
        }
        if (parameters.containsKey("dep_date")) {
            Value dep_date = parameters.get("dep_date");
        } else {
            shouldGoToPageDepTimeReq = true; 
        }
        if (parameters.containsKey("return_date")) {
            Value return_date = parameters.get("return_date");
        } else {
            shouldGoToPageReturnTimeReq = true;
        }
        if (parameters.containsKey("ticketnum")) {
            Value ticketNum = parameters.get("ticketnum");
        } else {
            shouldGoToPageReturnAccompanyNumReq = true;
        }

        WebhookResponse.Builder responseBuilder = WebhookResponse.newBuilder();

        if (shouldGoToPageFlightOffers) {
            logger.info("All 111");
            if (!currentPage.equals(PageFlightOffers)){
                responseBuilder.setTargetPage(PageFlightOffers);
            } 
            ResponseMessage.Text textMessage = ResponseMessage.Text.newBuilder()
                .addText("No problem, is it a one way trip or round trip?")
                .build();
            
            ResponseMessage responseMessage = ResponseMessage.newBuilder()
                .setText(textMessage)
                .build();

            FulfillmentResponse fulfillmentResponse = FulfillmentResponse.newBuilder()
                .addMessages(responseMessage)
                .build();

            responseBuilder.setFulfillmentResponse(fulfillmentResponse);
            
        } else if(shouldGoToPageLocationReq){
            logger.info("All 222");
            logger.info("currentPage:"+currentPage);
            logger.info("PageLocationReq:"+PageLocationReq);
            if (!currentPage.equals(PageLocationReq)){
                responseBuilder.setTargetPage(PageLocationReq);
            }
            ResponseMessage.Text textMessage = ResponseMessage.Text.newBuilder()
                .addText("Can I have your location?")
                .build();
            
            ResponseMessage responseMessage = ResponseMessage.newBuilder()
                .setText(textMessage)
                .build();

            FulfillmentResponse fulfillmentResponse = FulfillmentResponse.newBuilder()
                .addMessages(responseMessage)
                .build();

            responseBuilder.setFulfillmentResponse(fulfillmentResponse);
            
        } else if(shouldGoToPageDestinationReq){
            logger.info("All 333");
            if (!currentPage.equals(PageDestinationReq)){
                responseBuilder.setTargetPage(PageDestinationReq);
            }
            ResponseMessage.Text textMessage = ResponseMessage.Text.newBuilder()
                .addText("Can I have your destination?")
                .build();
            
            ResponseMessage responseMessage = ResponseMessage.newBuilder()
                .setText(textMessage)
                .build();

            FulfillmentResponse fulfillmentResponse = FulfillmentResponse.newBuilder()
                .addMessages(responseMessage)
                .build();

            responseBuilder.setFulfillmentResponse(fulfillmentResponse);
            
        } else if(shouldGoToPageDepTimeReq){
            logger.info("All 444");
            if (!currentPage.equals(PageDepTimeReq)){
                responseBuilder.setTargetPage(PageDepTimeReq);
            }
            ResponseMessage.Text textMessage = ResponseMessage.Text.newBuilder()
                .addText("When do you plan to start your journey?")
                .build();
            
            ResponseMessage responseMessage = ResponseMessage.newBuilder()
                .setText(textMessage)
                .build();

            FulfillmentResponse fulfillmentResponse = FulfillmentResponse.newBuilder()
                .addMessages(responseMessage)
                .build();

            responseBuilder.setFulfillmentResponse(fulfillmentResponse);
            

        } else if(shouldGoToPageReturnTimeReq){
            logger.info("All 555");
            if (!currentPage.equals(PageReturnTimeReq)){
                responseBuilder.setTargetPage(PageReturnTimeReq);
            }
            ResponseMessage.Text textMessage = ResponseMessage.Text.newBuilder()
                .addText("When do you plan to return?")
                .build();
            
            ResponseMessage responseMessage = ResponseMessage.newBuilder()
                .setText(textMessage)
                .build();

            FulfillmentResponse fulfillmentResponse = FulfillmentResponse.newBuilder()
                .addMessages(responseMessage)
                .build();

            responseBuilder.setFulfillmentResponse(fulfillmentResponse);
            
        } else if(shouldGoToPageReturnAccompanyNumReq){
            logger.info("All 666");
            if (!currentPage.equals(PageReturnAccompanyNumReq)){
                responseBuilder.setTargetPage(PageReturnAccompanyNumReq);
            }
            ResponseMessage.Text textMessage = ResponseMessage.Text.newBuilder()
                .addText("How many tickets do you want to book?")
                .build();
            
            ResponseMessage responseMessage = ResponseMessage.newBuilder()
                .setText(textMessage)
                .build();

            FulfillmentResponse fulfillmentResponse = FulfillmentResponse.newBuilder()
                .addMessages(responseMessage)
                .build();

            responseBuilder.setFulfillmentResponse(fulfillmentResponse);
            
        } else {
            logger.info("All information collected");
            if (!currentPage.equals(PageGetFlight)){
                responseBuilder.setTargetPage(PageGetFlight);
            }
            ResponseMessage.Text textMessage = ResponseMessage.Text.newBuilder()
                .addText("All information collected. Searching flights for you.")
                .build();
            
            ResponseMessage responseMessage = ResponseMessage.newBuilder()
                .setText(textMessage)
                .build();

            FulfillmentResponse fulfillmentResponse = FulfillmentResponse.newBuilder()
                .addMessages(responseMessage)
                .build();

            responseBuilder.setFulfillmentResponse(fulfillmentResponse);
            
            // invoke the flight offer API
            String flightOffers = flightOfferController.getFlightOffers(
                parameters.get("departure_city").getStringValue(),
                parameters.get("destination_city").getStringValue(),
                parameters.get("dep_date").getStringValue(),
                parameters.get("return_date").getStringValue(),
                Integer.parseInt(parameters.get("ticketnum").getStringValue()),
                1
            );

            ResponseMessage.Text flightOffersText = ResponseMessage.Text.newBuilder()
                .addText(flightOffers)
                .build();
            
            ResponseMessage flightOffersMessage = ResponseMessage.newBuilder()
                .setText(flightOffersText)
                .build();

            fulfillmentResponse = FulfillmentResponse.newBuilder()
                .addMessages(flightOffersMessage)
                .build();

            responseBuilder.setFulfillmentResponse(fulfillmentResponse);
        }


        return responseBuilder.build();
    }
}