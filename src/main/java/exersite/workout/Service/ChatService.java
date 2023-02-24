package exersite.workout.Service;

import exersite.workout.Controller.Dtos.ChatMessageDto;
import exersite.workout.Controller.Dtos.ChatRoomDto;
import exersite.workout.Domain.Chat.ChatMembers;
import exersite.workout.Domain.Chat.ChatMessage;
import exersite.workout.Domain.Chat.ChatRoom;
import exersite.workout.Domain.Member.Member;
import exersite.workout.Repository.Chat.ChatMembersRepository;
import exersite.workout.Repository.Chat.ChatMessageRepository;
import exersite.workout.Repository.Chat.ChatRoomRepository;
import exersite.workout.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final MemberRepository memberRepository;
    private final ChatMembersRepository chatMembersRepository;

    public List<ChatRoomDto> findAllChatRoomByDto(Member member) {
        return chatRoomRepository.findAllByMember(member.getId())
                .stream().map(chatRoom -> new ChatRoomDto(chatRoom)).collect(Collectors.toList());
    }

    public List<ChatMessageDto> findAllMessageByChatRoomIdDtos(Long roomId) {
        List<ChatMessage> chatMessages = chatMessageRepository.findAllByChatRoomId(roomId);
        return chatMessages.stream()
                .map(chatMessage -> new ChatMessageDto(chatMessage)).collect(Collectors.toList());
    }

    @Transactional
    public ChatRoom createChatRoomProcess(Member loginUser, Long otherUserId) {
        Member otherUser = memberRepository.findOne(otherUserId);
        ChatRoom saveChatRoom = saveChatRoom(loginUser, otherUser);
        saveChatMembers(loginUser, otherUser, saveChatRoom);
        return saveChatRoom;
    }

    private ChatRoom saveChatRoom(Member loginUser, Member otherUser) {
        String chatRoomName = loginUser.getNickname() + " " + otherUser.getNickname();
        ChatRoom chatRoom = ChatRoom.createChatRoom(chatRoomName);
        return chatRoomRepository.save(chatRoom);
    }

    private void saveChatMembers(Member loginUser, Member otherUser, ChatRoom saveChatRoom) {
        ChatMembers user1 = ChatMembers.createChatMembers(loginUser, saveChatRoom);
        ChatMembers user2 = ChatMembers.createChatMembers(otherUser, saveChatRoom);
        chatMembersRepository.saveAll(List.of(user1, user2));
    }

    // ChatMessageDto를 활용해서 ChatMessage 저장
    @Transactional
    public ChatMessageDto saveChatMessage(Long roomId, ChatMessageDto chatMessageDto) {
        Member member = memberRepository.findByNickname(chatMessageDto.getSender());
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).get();
        ChatMessage chatMessage = ChatMessage.createChatMessage(chatMessageDto.getMessage(), member, chatRoom,
                chatMessageDto.getType());
        ChatMessage saveChatMessage = chatMessageRepository.save(chatMessage);
        return new ChatMessageDto(saveChatMessage);
    }
}