package com.springboot.FlomadAIplanner.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springboot.FlomadAIplanner.DatabaseManagement.ConversationRepository;
import com.springboot.FlomadAIplanner.DatabaseManagement.NewMessage;
import com.springboot.FlomadAIplanner.DatabaseManagement.Speaker;

////////////////////////////////////////////
/**
 * @class
 * @description This is a Jpa object, where you can define datebase operation functions like findByUserId, Jpa object will read the function
 * name and infer the intent of coder. This is an interface with the database. This service operated the message table.
 */
//////////////////////////////////////////
@Service
public class ConversationService {
    @Autowired
    private ConversationRepository repository;

    public NewMessage saveMessage(String id, String message,LocalDateTime timestamp,Speaker speaker ) {
        NewMessage messages = new NewMessage();
        messages.setSpeaker(speaker);
        messages.setContent(message);
        messages.setTimestamp(timestamp);
        messages.setSessionId(id);

        return repository.save(messages);
    }

    public List<NewMessage> getMessagesBySessionId(String sessionId) {
        return repository.findBySessionIdOrderByTimestampAsc(sessionId);
    }
}
