package exersite.workout.Repository.Chat;

import exersite.workout.Domain.Chat.ChatRoom;

import java.util.List;

public interface ChatRoomRepositoryCustom {

    public List<ChatRoom> findAllByMember(Long memberId);
}
