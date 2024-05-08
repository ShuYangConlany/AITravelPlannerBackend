package com.springboot.FlomadAIplanner.DialogflowWebhookService;

import java.io.BufferedWriter;

import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import java.io.IOException;
import com.google.cloud.dialogflow.cx.v3beta1.WebhookResponse;
import com.google.protobuf.Struct;
import com.google.protobuf.Value;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.protobuf.util.JsonFormat;

import com.google.cloud.dialogflow.cx.v3beta1.ResponseMessage;
import com.google.cloud.dialogflow.cx.v3beta1.WebhookResponse.FulfillmentResponse;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.util.Collections;


public class WbhookFlightJumpPage implements HttpFunction {
  private static final Logger logger = Logger.getLogger(WbhookFlightJumpPage.class.getName());
  public void service(final HttpRequest request, final HttpResponse response) throws Exception {
    logger.info("进来了哦");
    final BufferedWriter writer = response.getWriter();
    // writer.write("Hello world!");

    String requestBody = request.getReader().lines().collect(Collectors.joining());
    logger.info("Request Body: " + requestBody);
    // 解析JSON字符串为JsonObject
    JsonObject json = JsonParser.parseString(requestBody).getAsJsonObject();
    logger.info("JSON Object: " + json.toString());
    String currentPage = json.getAsJsonObject("pageInfo").get("currentPage").getAsString();
    logger.info("currentPage:"+currentPage);
    // 假设Dialogflow发送的参数在一个名为'parameters'的JSON对象中
    JsonObject intentInfo = json.getAsJsonObject("intentInfo");
    JsonObject parametersJson = null;
    Map<String, Value> parameters = new HashMap<>();
    JsonObject sessionInfo = json.getAsJsonObject("sessionInfo");
    if (sessionInfo != null && sessionInfo.has("parameters")) {
        JsonObject sessionParametersJson = sessionInfo.getAsJsonObject("parameters");
        logger.info("Session Parameters JSON: " + sessionParametersJson.toString());
        for (String key : sessionParametersJson.keySet()) {
            JsonElement value = sessionParametersJson.get(key);
            // 同样，简化处理：仅处理字符串类型的值
            if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isString()) {
                // 如果 intent parameters 已经设置了某个 key，这里会覆盖它
                parameters.put(key, Value.newBuilder().setStringValue(value.getAsString()).build());
            }
        }
    }
    if (intentInfo != null && intentInfo.get("parameters") != null) {
        parametersJson = intentInfo.getAsJsonObject("parameters");
        logger.info("Parameters JSON: " + parametersJson.toString());
        for (String key : parametersJson.keySet()) {
            JsonElement value = parametersJson.get(key);
            // 这里简化处理：仅处理字符串类型的值
            if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isString()) {
                parameters.put(key, Value.newBuilder().setStringValue(value.getAsString()).build());
            }
        }
    }  
    logger.info("parameters" + parameters.toString());
    WebhookResponse webhookResponse = handleWebhookRequest(parameters,currentPage);
    String webhookResponseJson = JsonFormat.printer().print(webhookResponse);
    writer.write(webhookResponseJson);
  }

    public WebhookResponse handleWebhookRequest(Map<String, Value> parameters, String currentPage) {
      // 检查参数A是否存在或满足某个条件
        boolean shouldGoToPageFlightOffers = false; // 假设这是判断条件
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

        // 构建响应
        WebhookResponse.Builder responseBuilder = WebhookResponse.newBuilder();

        if (shouldGoToPageFlightOffers) {
            logger.info("All 111");
            if (!currentPage.equals(PageFlightOffers)){
                responseBuilder.setTargetPage(PageFlightOffers);
            } 
            ResponseMessage.Text textMessage = ResponseMessage.Text.newBuilder()
                .addText("No problem, is it a one way trip or round trip?")
                .build();
            
            // 将文本消息包装在 ResponseMessage 中
            ResponseMessage responseMessage = ResponseMessage.newBuilder()
                .setText(textMessage)
                .build();

            // 创建充实响应并将消息添加进去
            FulfillmentResponse fulfillmentResponse = FulfillmentResponse.newBuilder()
                .addMessages(responseMessage)
                .build();

            // 将充实响应设置到 webhook 响应中
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
            
            // 将文本消息包装在 ResponseMessage 中
            ResponseMessage responseMessage = ResponseMessage.newBuilder()
                .setText(textMessage)
                .build();

            // 创建充实响应并将消息添加进去
            FulfillmentResponse fulfillmentResponse = FulfillmentResponse.newBuilder()
                .addMessages(responseMessage)
                .build();

            // 将充实响应设置到 webhook 响应中
            responseBuilder.setFulfillmentResponse(fulfillmentResponse);
            
        } else if(shouldGoToPageDestinationReq){
            logger.info("All 333");
            if (!currentPage.equals(PageDestinationReq)){
                responseBuilder.setTargetPage(PageDestinationReq);
            }
            ResponseMessage.Text textMessage = ResponseMessage.Text.newBuilder()
                .addText("Can I have your destination?")
                .build();
            
            // 将文本消息包装在 ResponseMessage 中
            ResponseMessage responseMessage = ResponseMessage.newBuilder()
                .setText(textMessage)
                .build();

            // 创建充实响应并将消息添加进去
            FulfillmentResponse fulfillmentResponse = FulfillmentResponse.newBuilder()
                .addMessages(responseMessage)
                .build();

            // 将充实响应设置到 webhook 响应中
            responseBuilder.setFulfillmentResponse(fulfillmentResponse);
            
        } else if(shouldGoToPageDepTimeReq){
            logger.info("All 444");
            if (!currentPage.equals(PageDepTimeReq)){
                responseBuilder.setTargetPage(PageDepTimeReq);
            }
            ResponseMessage.Text textMessage = ResponseMessage.Text.newBuilder()
                .addText("When do you plan to start your journey?")
                .build();
            
            // 将文本消息包装在 ResponseMessage 中
            ResponseMessage responseMessage = ResponseMessage.newBuilder()
                .setText(textMessage)
                .build();

            // 创建充实响应并将消息添加进去
            FulfillmentResponse fulfillmentResponse = FulfillmentResponse.newBuilder()
                .addMessages(responseMessage)
                .build();

            // 将充实响应设置到 webhook 响应中
            responseBuilder.setFulfillmentResponse(fulfillmentResponse);
            

        } else if(shouldGoToPageReturnTimeReq){
            logger.info("All 555");
            if (!currentPage.equals(PageReturnTimeReq)){
                responseBuilder.setTargetPage(PageReturnTimeReq);
            }
            ResponseMessage.Text textMessage = ResponseMessage.Text.newBuilder()
                .addText("When do you plan to return?")
                .build();
            
            // 将文本消息包装在 ResponseMessage 中
            ResponseMessage responseMessage = ResponseMessage.newBuilder()
                .setText(textMessage)
                .build();

            // 创建充实响应并将消息添加进去
            FulfillmentResponse fulfillmentResponse = FulfillmentResponse.newBuilder()
                .addMessages(responseMessage)
                .build();

            // 将充实响应设置到 webhook 响应中
            responseBuilder.setFulfillmentResponse(fulfillmentResponse);
            
        } else if(shouldGoToPageReturnAccompanyNumReq){
            logger.info("All 666");
            if (!currentPage.equals(PageReturnAccompanyNumReq)){
                responseBuilder.setTargetPage(PageReturnAccompanyNumReq);
            }
            ResponseMessage.Text textMessage = ResponseMessage.Text.newBuilder()
                .addText("How many tickets do you want to book?")
                .build();
            
            // 将文本消息包装在 ResponseMessage 中
            ResponseMessage responseMessage = ResponseMessage.newBuilder()
                .setText(textMessage)
                .build();

            // 创建充实响应并将消息添加进去
            FulfillmentResponse fulfillmentResponse = FulfillmentResponse.newBuilder()
                .addMessages(responseMessage)
                .build();

            // 将充实响应设置到 webhook 响应中
            responseBuilder.setFulfillmentResponse(fulfillmentResponse);
            
        } else {
            logger.info("All information collected");
            if (!currentPage.equals(PageGetFlight)){
                responseBuilder.setTargetPage(PageGetFlight);
            }
            ResponseMessage.Text textMessage = ResponseMessage.Text.newBuilder()
                .addText("All information collected. Searching flights for you.")
                .build();
            
            // 将文本消息包装在 ResponseMessage 中
            ResponseMessage responseMessage = ResponseMessage.newBuilder()
                .setText(textMessage)
                .build();

            // 创建充实响应并将消息添加进去
            FulfillmentResponse fulfillmentResponse = FulfillmentResponse.newBuilder()
                .addMessages(responseMessage)
                .build();

            // 将充实响应设置到 webhook 响应中
            responseBuilder.setFulfillmentResponse(fulfillmentResponse);
            
        }

        // 返回构建的响应
        return responseBuilder.build();
    }
    public String callFlightOfferAPI(Map<String, Value> parameters) {
        RestTemplate restTemplate = new RestTemplate();

        String origin = parameters.get("departure_city").getStringValue();
        String destination = parameters.get("destination_city").getStringValue();
        String departureDate = parameters.get("dep_date").getStringValue();
        String returnDate = parameters.get("return_date").getStringValue();
        int adults = Integer.parseInt(parameters.get("ticketnum").getStringValue());
        int max = 1; // 可以根据需要调整

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth("your_access_token_here"); // 这里你需要正确设置访问令牌

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        String url = "https://test.api.amadeus.com/v2/shopping/flight-offers" +
                    "?originLocationCode={origin}" +
                    "&destinationLocationCode={destination}" +
                    "&departureDate={departureDate}" +
                    "&returnDate={returnDate}" +
                    "&adults={adults}" +
                    "&max={max}";

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class,
                origin, destination, departureDate, returnDate, adults, max);

        return response.getBody();
    }
    public String getAuthToken() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://26c5-2601-19b-280-fa70-41d3-8eab-f536-4c13.ngrok-free.app/auth/token";
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                logger.warning("Failed to retrieve Auth Token: " + response.getStatusCode());
                return null;
            }
        } catch (Exception e) {
            logger.severe("Error while fetching Auth Token: " + e.getMessage());
            return null;
        }
    }
}
