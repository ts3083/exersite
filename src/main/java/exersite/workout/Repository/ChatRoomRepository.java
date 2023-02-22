package exersite.workout.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import exersite.workout.Domain.Chat.ChatRoom;
import exersite.workout.Domain.Chat.QChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static exersite.workout.Domain.Chat.QChatRoom.chatRoom;
import static exersite.workout.Domain.Chat.QChatMembers.chatMembers;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public void save(ChatRoom chatRoom) {
        em.persist(chatRoom);
    }

    public ChatRoom findById(Long chatRoomId) {
        ChatRoom chatRoom = em.find(ChatRoom.class, chatRoomId);
        if (chatRoom == null) {
            throw new InvalidDataAccessApiUsageException("삭제된 채팅방입니다");
        }
        return chatRoom;
    }

    public List<ChatRoom> findAllByMember(Long memberId) {
        // select cr from ChatMembers cm join cm.chatRoom cr on cm.member.id = memberId
        return queryFactory
                .select(chatRoom)
                .from(chatMembers)
                .join(chatMembers.chatRoom, chatRoom)
                .on(chatMembers.member.id.eq(memberId))
                .fetch();
    }

    public ChatRoom findByRoomId(String roomId) {
        return queryFactory
                .selectFrom(chatRoom)
                .where(chatRoom.roomId.eq(roomId))
                .fetchOne();
    }
}
