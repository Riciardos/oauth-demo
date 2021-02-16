package com.matchesfashion.oauthdemo.service;

import com.matchesfashion.oauthdemo.data.ChatMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService
{
    private List<ChatMessage> chatMessageList = new ArrayList<>();

    public List<ChatMessage> getAllMessages() {
        return chatMessageList.stream()
                .sorted((chatMessage1, chatMessage2) -> {
                    if (chatMessage1.getLocalDateTime().isBefore(chatMessage2.getLocalDateTime())) {
                        return -1;
                    } else {
                        return 1;
                    }
                })
                .collect(Collectors.toList());
    }

    public void addMessage(ChatMessage message) {
        chatMessageList.add(message);
    }
}
