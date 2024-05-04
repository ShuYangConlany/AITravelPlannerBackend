package com.springboot.FlomadAIplanner.DatabaseManagement;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

////////////////////////////////////////////
/**
 * @class
 * @description This is a Jpa object, where you can define datebase operation functions like findBySessionIdOrderByTimestampAsc, Jpa object will read the function
 * name and infer the intent of coder.
 */
//////////////////////////////////////////
public interface ConversationRepository extends JpaRepository<NewMessage, Integer> {
    List<NewMessage> findBySessionIdOrderByTimestampAsc(String session_id);
}
