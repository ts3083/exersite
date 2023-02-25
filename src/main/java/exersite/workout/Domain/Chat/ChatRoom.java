package exersite.workout.Domain.Chat;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter
public class ChatRoom {

    @Id @GeneratedValue
    @Column(name = "chatRoom_id")
    private Long id;

    @Column(unique = true)
    private String roomName;
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private Set<ChatMembers> chatMembers = new HashSet<>();

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<ChatMessage> chatMessages = new ArrayList<>();

    public static ChatRoom createChatRoom(String roomName) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setRoomName(roomName);
        return chatRoom;
    }
}
