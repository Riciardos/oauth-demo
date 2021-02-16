package com.matchesfashion.oauthdemo.controller;

import com.matchesfashion.oauthdemo.service.ChatService;
import com.matchesfashion.oauthdemo.data.ChatMessage;
import com.matchesfashion.oauthdemo.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController()
@CrossOrigin("http://localhost:3000")
public class ChatController {

    @Autowired
    private ChatService chatService;


    @RequestMapping(value = "/api/get-messages", method = RequestMethod.GET)
    public ResponseEntity<List<ChatMessage>> getMessage() {
        return ResponseEntity.of(Optional.of(chatService.getAllMessages()));
    }

    @RequestMapping(value = "/api/post-message", method = RequestMethod.POST)
    public void postMessage(@AuthenticationPrincipal UserPrincipal userPrincipal,
                            @RequestBody String message) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessage(message);
        chatMessage.setLocalDateTime(LocalDateTime.now());
        chatMessage.setPrincipal(userPrincipal);
        chatService.addMessage(chatMessage);
    }
}
