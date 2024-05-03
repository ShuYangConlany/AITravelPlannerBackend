package com.springboot.FlomadAIplanner.DatabaseManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

////////////////////////////////////////////
/**
 * @class
 * @description This is a Jpa object, where you can define datebase operation functions like findByUserId, Jpa object will read the function
 * name and infer the intent of coder. This is an interface with the database.
 */
//////////////////////////////////////////
public interface SessionRepository extends JpaRepository<NewSession, String> {
    List<NewSession> findByUserId(String userId);
    NewSession findBySessionIdAndUserId(String sessionId, String userId); //For updating the session's tittle and timestamp
}