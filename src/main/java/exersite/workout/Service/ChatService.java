package exersite.workout.Service;

import exersite.workout.Controller.Dtos.ChatMessageDto;
import exersite.workout.Controller.Dtos.ChatRoomDto;
import exersite.workout.Controller.Forms.ChatRoomForm;
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
@Transactional
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final MemberRepository memberRepository;
    private final ChatMembersRepository chatMembersRepository;

    @Transactional(readOnly = true)
    public List<ChatRoomDto> findAllChatRoomByDto(Member member) {
        return chatRoomRepository.findAllByMember(member.getId())
                .stream().map(chatRoom -> new ChatRoomDto(chatRoom)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ChatMessageDto> findAllMessageByChatRoomIdDtos(Long roomId) {
        List<ChatMessage> chatMessages = chatMessageRepository.findAllByChatRoomId(roomId);
        return chatMessages.stream()
                .map(chatMessage -> new ChatMessageDto(chatMessage)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ChatRoomDto> findAllChatRoomDtos() {
        return chatRoomRepository.findAll()
                .stream().map(chatRoom -> new ChatRoomDto(chatRoom)).collect(Collectors.toList());
    }

    public ChatRoom createChatRoomProcess(Member loginUser, ChatRoomForm chatRoomForm) {
        ChatRoom saveChatRoom = saveChatRoom(chatRoomForm.getRoomName()); // chatRoom 저장
        saveChatMembers(loginUser, saveChatRoom);
        return saveChatRoom;
    }

    private ChatRoom saveChatRoom(String chatRoomName) {
        ChatRoom chatRoom = ChatRoom.createChatRoom(chatRoomName);
        return chatRoomRepository.save(chatRoom);
    }

    private void saveChatMembers(Member member, ChatRoom saveChatRoom) {
        ChatMembers user = ChatMembers.createChatMembers(member, saveChatRoom);
        chatMembersRepository.save(user);
    }

    // ChatMessageDto를 활용해서 ChatMessage 저장
    public ChatMessageDto saveChatMessage(Long roomId, ChatMessageDto chatMessageDto) {
        Member member = memberRepository.findByNickname(chatMessageDto.getSender());
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).get();
        ChatMessage chatMessage = ChatMessage.createChatMessage(chatMessageDto.getMessage(), member, chatRoom,
                chatMessageDto.getType());
        ChatMessage saveChatMessage = chatMessageRepository.save(chatMessage);
        return new ChatMessageDto(saveChatMessage);
    }

    // 채팅방 참여를 누른 사용자가 이미 채팅방 멤버인지 확인하고 아니라면 저장
    public void checkChatMembers(Long roomId, Member member) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).get();
        boolean exists = chatMembersRepository.existsByChatRoomAndMember(chatRoom, member);
        if (!exists) { // 채팅방에 처음 들어가는 거라면 chatMembers에 저장
            saveChatMembers(member, chatRoom);
        }
    }
}
