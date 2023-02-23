package exersite.workout.Repository.Chat;

import exersite.workout.Domain.Chat.ChatMembers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMembersRepository extends JpaRepository<ChatMembers, Long> {
}
