package exersite.workout.Domain.Chat;

import exersite.workout.Domain.Member.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class ChatMembers {

    @Id @GeneratedValue
    @Column(name = "chatMembers_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatRoom_id")
    private ChatRoom chatRoom;

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
        chatRoom.getChatMembers().add(this);
    }

    public static ChatMembers createChatMembers(Member member, ChatRoom chatRoom) {
        ChatMembers chatMembers = new ChatMembers();
        chatMembers.setMember(member);
        chatMembers.setChatRoom(chatRoom);
        return chatMembers;
    }
}
