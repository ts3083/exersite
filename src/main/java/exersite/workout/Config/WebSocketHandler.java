package exersite.workout.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import exersite.workout.Controller.Dtos.ChatMessageDto;
import exersite.workout.Controller.Dtos.ChatRoomDto;
import exersite.workout.Domain.Chat.ChatRoom;
import exersite.workout.Repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {

    private final ChatRoomRepository chatRoomRepository;
    private final ObjectMapper objectMapper;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String msg = message.getPayload();
        ChatMessageDto chatMessageDto = objectMapper.readValue(msg, ChatMessageDto.class);
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(chatMessageDto.getRoomId());
        ChatRoomDto chatRoomDto = new ChatRoomDto(chatRoom);
        chatRoomDto.handleMessage(session, chatMessageDto, objectMapper);
    }
}
