package exersite.workout.Controller.Dtos;

import exersite.workout.Domain.Chat.ChatMessage;
import exersite.workout.Domain.Chat.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageDto {

    private Long roomId;
    private String sender;
    private String message;
    private MessageType type;
    private LocalDateTime sendDateTime;

    public ChatMessageDto(ChatMessage chatMessage) {
        this.roomId = chatMessage.getId();
        this.sender = chatMessage.getMember().getNickname();
        this.message = chatMessage.getMessage();
        this.type = chatMessage.getType();
        this.sendDateTime = chatMessage.getSendDate();
    }
}
