package exersite.workout.Controller.Dtos;

import exersite.workout.Domain.Chat.ChatMessage;
import exersite.workout.Domain.Chat.MessageType;
import lombok.Data;

@Data
public class ChatMessageDto {

    private String roomId;
    private String writer;
    private String message;
    private MessageType type;

    public static ChatMessageDto createChatMessageDto(ChatMessage chatMessage) {
        ChatMessageDto chatMessageDto = new ChatMessageDto();
        chatMessageDto.setRoomId(chatMessage.getChatRoom().getRoomId());
        chatMessageDto.setWriter(chatMessage.getMember().getNickname());
        chatMessageDto.setMessage(chatMessage.getMessage());
        chatMessageDto.setType(chatMessage.getType());
        return chatMessageDto;
    }
}
