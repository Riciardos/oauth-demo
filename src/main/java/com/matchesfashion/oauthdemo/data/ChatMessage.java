package com.matchesfashion.oauthdemo.data;

import com.matchesfashion.oauthdemo.security.UserPrincipal;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatMessage {

    private String message;
    private LocalDateTime localDateTime;
    private UserPrincipal principal;
}
