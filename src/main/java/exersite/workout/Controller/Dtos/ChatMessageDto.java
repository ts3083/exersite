package exersite.workout.Controller.Dtos;

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

    private String roomId;
    private String sender;
    private String message;
    private MessageType type;
    private LocalDateTime sendDateTime;
}
