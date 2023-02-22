package exersite.workout.Service;

import exersite.workout.Controller.Dtos.ChatRoomDto;
import exersite.workout.Domain.Member.Member;
import exersite.workout.Repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;

    public List<ChatRoomDto> findAllChatRoomByDto(Member member) {
        return chatRoomRepository.findAllByMember(member.getId())
                .stream().map(chatRoom -> new ChatRoomDto(chatRoom)).collect(Collectors.toList());
    }
}
