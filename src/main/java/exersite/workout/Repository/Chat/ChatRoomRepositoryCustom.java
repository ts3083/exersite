package exersite.workout.Repository.Chat;

import exersite.workout.Domain.Chat.ChatRoom;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface ChatRoomRepositoryCustom {

    public List<ChatRoom> findAllByMember(Long memberId);
}
