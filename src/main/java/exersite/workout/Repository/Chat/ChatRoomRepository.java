package exersite.workout.Repository.Chat;

import exersite.workout.Domain.Chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>,ChatRoomRepositoryCustom {

    ChatRoom findByRoomName(String roomName);
}
