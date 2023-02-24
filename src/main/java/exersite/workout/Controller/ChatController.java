package exersite.workout.Controller;

import exersite.workout.Config.CurrentUser;
import exersite.workout.Controller.Dtos.ChatMessageDto;
import exersite.workout.Controller.Dtos.ChatRoomDto;
import exersite.workout.Domain.Chat.ChatRoom;
import exersite.workout.Domain.Member.Member;
import exersite.workout.Service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    /**
     * 내 프로필에서 내가 참여하고 있는 채팅방 목록 보여주기
     * 로그인한 Member와 chatMembers를 통해 CurrentUser가 참여하고 있는 chatRoom 목록 전달
     * */
    @GetMapping("/chatRooms")
    public String chatRoom(@CurrentUser Member member, Model model) {
        List<ChatRoomDto> chatRoomByDto = chatService.findAllChatRoomByDto(member);
        model.addAttribute("chatRooms", chatRoomByDto);
        return "chat/chatRoomList";
    }

    /**
     * 이미 속해있는 채팅방으로 이동
     * model : 이전 채팅 기록 + roomId
     * */
    @GetMapping("/{roomId}")
    public String openChatRoom(@PathVariable Long roomId, Model model) {
        List<ChatMessageDto> chatMessageDtos = chatService.findAllMessageByChatRoomIdDtos(roomId);
        model.addAttribute("roomId", roomId);
        model.addAttribute("chatMessageDtos", chatMessageDtos);
        return "chat/chatRoom";
    }

    /**
     * 새로운 채팅방 생성
     * 다른 user의 페이지에서 채팅하기 버튼을 클릭하면 chatRoom으로 이동
     * chatMembers, chatRoom에 데이터 저장
     * */
    @PostMapping("/chatRoom/new/{otherUserId}")
    public String createChatRoom(@PathVariable Long otherUserId, @CurrentUser Member member) {
        // chatRoom 저장
        // chatRoom, Member로 chatMembers 저장
        ChatRoom saveChatRoom = chatService.createChatRoomProcess(member, otherUserId);
        return "redirect:/" + saveChatRoom.getId();
    }
}
