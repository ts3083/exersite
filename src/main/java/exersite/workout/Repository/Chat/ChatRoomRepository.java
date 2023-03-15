package exersite.workout.Repository.Chat;

import exersite.workout.Domain.Chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>,ChatRoomRepositoryCustom {

    ChatRoom findByRoomName(String roomName);

    boolean existsByRoomName(String roomName);
}
