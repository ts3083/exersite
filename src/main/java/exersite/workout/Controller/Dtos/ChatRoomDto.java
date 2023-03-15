package exersite.workout.Controller.Dtos;

import exersite.workout.Domain.Chat.ChatRoom;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatRoomDto {

    private Long roomId;
    private String roomName;

    public ChatRoomDto(ChatRoom chatRoom) {
        this.roomId = chatRoom.getId();
        this.roomName = chatRoom.getRoomName();
    }
}
