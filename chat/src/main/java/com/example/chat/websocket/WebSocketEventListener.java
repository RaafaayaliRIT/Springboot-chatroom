package com.example.chat.websocket;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.example.chat.chat.ChatMessage;
import com.example.chat.chat.MessageType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component  // seach this
@RequiredArgsConstructor // and this
@Slf4j // <-- moon runes lol (used for when user leaves the chat)
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messsageTemplate;
    @EventListener 
    public void handleWebSocketDisconnectListner(
        SessionDisconnectEvent event
    ){
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String)headerAccessor.getSessionAttributes().get("username");
        if(username != null){
            
            log.info("User disconncected: {}", username);
            
            var chatMessage = ChatMessage.builder()
                .type(MessageType.LEAVE)
                .sender(username)
                .build();
            messsageTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }
}
