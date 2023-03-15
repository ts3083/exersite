package exersite.workout.Repository.Chat;

import com.querydsl.jpa.impl.JPAQueryFactory;
import exersite.workout.Domain.Chat.ChatRoom;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static exersite.workout.Domain.Chat.QChatMembers.chatMembers;
import static exersite.workout.Domain.Chat.QChatRoom.chatRoom;

@RequiredArgsConstructor
public class ChatRoomRepositoryImpl implements ChatRoomRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    /**
     * chatMembers와 chatRoom을 조인하여 CurrentUser가 속한 chatRoom 리스트를 조회
     * */
    public List<ChatRoom> findAllByMember(Long memberId) {
        // select cr from ChatMembers cm join cm.chatRoom cr on cm.member.id = memberId
        return queryFactory
                .select(chatRoom)
                .from(chatMembers)
                .join(chatMembers.chatRoom, chatRoom)
                .on(chatMembers.member.id.eq(memberId))
                .fetch();
    }
}
