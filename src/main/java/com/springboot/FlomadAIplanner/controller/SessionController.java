package com.springboot.FlomadAIplanner.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.FlomadAIplanner.DatabaseManagement.NewSession;
import com.springboot.FlomadAIplanner.DatabaseManagement.SessionDto;
import com.springboot.FlomadAIplanner.service.SessionService;

import java.util.List;

////////////////////////////////////////////
/**
 * @class
 * @description provide a controller for the front end that can operate the database
 */
//////////////////////////////////////////
@RestController
public class SessionController {
    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    /**
   * @method
   * @description  get all the id of all sessions under a given user 
   */
    @GetMapping("/users/{userId}/sessions")
    public List<SessionDto> getSessionIdsByUserId(@PathVariable String userId) {
        return sessionService.findSessionIdsByUserId(userId);
    }

    /**
   * @method
   * @description add a new line in the session table
   */
    @PostMapping("/add_session")
    public ResponseEntity<NewSession> addSession(@RequestBody NewSession session) {
        if (session.getSessionId() == null || session.getSessionId().isEmpty()) {
            throw new RuntimeException("Session ID is not provided.");
        }
        NewSession savedSession = sessionService.saveSession(session);
        return ResponseEntity.ok(savedSession);
    }

    @PostMapping("/users/{userId}/sessions/{sessionId}")
    public ResponseEntity<NewSession> updateSession(@PathVariable String userId, @PathVariable String sessionId,
                                                    @RequestBody SessionDto sessionDto) {
        NewSession updatedSession = sessionService.updateSession(sessionId, userId, sessionDto.getTitle(), sessionDto.getTimestamp());
        return ResponseEntity.ok(updatedSession);
    }
}