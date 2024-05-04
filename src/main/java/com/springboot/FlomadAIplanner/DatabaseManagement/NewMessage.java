package com.springboot.FlomadAIplanner.DatabaseManagement;
import java.time.LocalDateTime;
import jakarta.persistence.*;

////////////////////////////////////////////
/**
 * @class
 * @description This is an entity, a reflection of the database, provide a straight forward way to operate the database
 */
//////////////////////////////////////////
@Entity
@Table(name = "message")
public class NewMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private int messageId;

    @Column(name = "session_id", nullable = false)
    private String sessionId;

    @Column(name = "content")
    private String content;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    @Column(name = "speaker", nullable = false)
    private Speaker speaker;

    public NewMessage() {
    }

    public NewMessage(int messageId, String sessionId, Speaker speaker, String content, LocalDateTime timestamp) {
        this.messageId = messageId;
        this.sessionId = sessionId;
        this.speaker = speaker;
        this.content = content;
        this.timestamp = timestamp;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Speaker getSpeaker() {
        return speaker;
    }

    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
