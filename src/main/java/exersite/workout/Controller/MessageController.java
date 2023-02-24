package exersite.workout.Controller;

import exersite.workout.Controller.Dtos.ChatMessageDto;
import exersite.workout.Service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final ChatService chatService;

    @MessageMapping("/{roomId}") // 해당 주소로 전송되면 메서드 실행
    @SendTo("/room/{roomId}") // 해당 구독 주소로 메시지 전송
    public ChatMessageDto chat(@DestinationVariable Long roomId, ChatMessageDto chatMessageDto) {
        // 채팅메시지 저장
        return chatService.saveChatMessage(roomId, chatMessageDto);
    }
}
