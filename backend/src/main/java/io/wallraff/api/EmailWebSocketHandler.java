package io.wallraff.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.wallraff.data.EmailRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class EmailWebSocketHandler extends TextWebSocketHandler {

    private List<WebSocketSession> sessions = new CopyOnWriteArrayList<WebSocketSession>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        sessions.remove(session);
    }

    public void broadcastNewEmailMessage(EmailRecord email) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(new EmailListItem(email));
        for(WebSocketSession webSocketSession : sessions) {
            webSocketSession.sendMessage(new TextMessage(content));
        }
    }
}