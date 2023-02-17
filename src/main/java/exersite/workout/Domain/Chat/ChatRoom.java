package exersite.workout.Domain.Chat;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class ChatRoom {

    @Id @GeneratedValue
    private Long id;
    private String roomId;
    private String name; // 채팅방 이름 - 채팅하는 사람의 nickname
    // room과 member의 관계 = m대n

}
