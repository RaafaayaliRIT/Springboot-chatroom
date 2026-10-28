package com.example.chat.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller 
public class ChatController {
    
    @MessageMapping("/chat.sendMessage") //end point
    @SendTo("/topic/public")// with be in queue
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage)
    {
        System.out.println(">>> sendMessage hit: " + chatMessage.getSender() + " - " + chatMessage.getContent()); // ./mvnw clean spring-boot:run  because sometimes you justneed to do that for some reason
        return chatMessage;
    }

    @MessageMapping("/chat.addUser") //end point
    @SendTo("/topic/public")// with be in queue
    public ChatMessage addUser(
        @Payload ChatMessage chatmessage,
        SimpMessageHeaderAccessor headerAccessor
    
    ){
        // Add username in web socket session 
        headerAccessor.getSessionAttributes().put("username", chatmessage.getSender());
        return chatmessage;
    }
}
