package exersite.workout.Repository.Chat;

import exersite.workout.Domain.Chat.ChatMembers;
import exersite.workout.Domain.Chat.ChatRoom;
import exersite.workout.Domain.Member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
public interface ChatMembersRepository extends JpaRepository<ChatMembers, Long> {

    public boolean existsByChatRoomAndMember(ChatRoom chatRoom, Member member);
}
