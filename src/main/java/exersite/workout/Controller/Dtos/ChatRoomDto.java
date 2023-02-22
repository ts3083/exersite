package exersite.workout.Controller.Dtos;

import com.fasterxml.jackson.databind.ObjectMapper;
import exersite.workout.Domain.Chat.ChatMessage;
import exersite.workout.Domain.Chat.ChatRoom;
import exersite.workout.Domain.Chat.MessageType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
public class ChatRoomDto {

    private String roomId;
    private String roomName;
    private Set<WebSocketSession> sessions = new HashSet<>();

    public ChatRoomDto(ChatRoom chatRoom) {
        this.roomId = chatRoom.getRoomId();
        this.roomName = chatRoom.getRoomName();
    }

    public static ChatRoomDto create(String roomName) {
        ChatRoomDto chatRoomDto = new ChatRoomDto();
        chatRoomDto.roomId = UUID.randomUUID().toString();
        chatRoomDto.roomName = roomName;
        return chatRoomDto;
    }

    private void send(ChatMessageDto chatMessageDto, ObjectMapper objectMapper)
            throws IOException {
        TextMessage textMessage = new TextMessage(objectMapper
                .writeValueAsString(chatMessageDto.getMessage()));
        for(WebSocketSession sess : sessions) {
            sess.sendMessage(textMessage);
        }
    }

    public void handleMessage(WebSocketSession sess, ChatMessageDto chatMessageDto,
                              ObjectMapper objectMapper) throws IOException {
        if(chatMessageDto.getType().equals(MessageType.ENTER)) {
            sessions.add(sess);
            chatMessageDto.setMessage(chatMessageDto.getWriter() +
                    "님이 입장하셨습니다.");
        } else if (chatMessageDto.getType().equals(MessageType.LEAVE)) {
            sessions.remove(sess);
            chatMessageDto.setMessage(chatMessageDto.getWriter() +
                    "님이 퇴장하셨습니다.");
        } else {
            chatMessageDto.setMessage(chatMessageDto.getWriter() +
                    " : " + chatMessageDto.getMessage());
        }
        send(chatMessageDto, objectMapper);
    }
}
