package com.springboot.FlomadAIplanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.FlomadAIplanner.service.AmadeusAuthService;

@RestController
public class AmadeusAuthController {

    private final AmadeusAuthService amadeusAuthService;

    @Autowired
    public AmadeusAuthController(AmadeusAuthService amadeusAuthService) {
        this.amadeusAuthService = amadeusAuthService;
    }

    @GetMapping("/auth/token")
    public ResponseEntity<String> getAuthToken() {
        String token = amadeusAuthService.getAccessToken();
        if (token != null) {
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.badRequest().body("Failed to retrieve access token");
        }
    }
}