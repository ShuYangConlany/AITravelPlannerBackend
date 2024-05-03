package com.springboot.FlomadAIplanner.DatabaseManagement;

import java.time.LocalDateTime;

////////////////////////////////////////////
/**
 * @class
 * @description This is a class store data retrived from database(all messages in a session) and ready to send to front end
 */
//////////////////////////////////////////
public class SessionDto {
    private String sessionId;
    private String userId;
    private String title;
    private LocalDateTime timestamp;

    // 构造函数
    public SessionDto(String sessionId, String userId, String title, LocalDateTime timestamp) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.title = title;
        this.timestamp = timestamp;
    }

    // Getter 和 Setter 方法
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
