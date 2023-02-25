package exersite.workout.Domain.Chat;

import exersite.workout.Domain.Member.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class ChatMessage {

    @Id @GeneratedValue
    @Column(name = "chatMessage_id")
    private Long id;
    private String message;
    private LocalDateTime sendDate;
    private MessageType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 메세지 작성자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatRoom_id")
    private ChatRoom chatRoom; // 메세지 작성한 채팅방

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
        chatRoom.getChatMessages().add(this);
    }

    public static ChatMessage createChatMessage(String message, Member member, ChatRoom chatRoom
                                                ,MessageType messageType) {
        ChatMessage chatMessage = new ChatMessage();
        if (messageType.equals(MessageType.ENTER)) {
            chatMessage.setMessage(member.getNickname() + "님이 입장하셨습니다.");
        } else if(messageType.equals(MessageType.LEAVE)) {
            chatMessage.setMessage(member.getNickname() + "님이 퇴장하셨습니다.");
        } else {
            chatMessage.setMessage(message);
        }
        chatMessage.setMember(member);
        chatMessage.setChatRoom(chatRoom);
        chatMessage.setType(messageType);
        chatMessage.setSendDate(LocalDateTime.now());
        return chatMessage;
    }
}
