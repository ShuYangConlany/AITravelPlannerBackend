package com.springboot.FlomadAIplanner.controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.logging.Logger;

@RestController
public class WebhookHelloWorld {

    private static final Logger logger = Logger.getLogger(WebhookHelloWorld.class.getName());
    // @PostMapping("/webhook")
    // public String handleWebhook(@RequestBody String request) {
    //     // 这里可以解析 request 中的数据，根据需要生成响应
    //     logger.info("Hello World!!!!!!!!!!!!!Received webhook request: " + request);
    //     return "Hello World";  // 简单返回一个字符串
    // }
}
