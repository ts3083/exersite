package exersite.workout.Controller;

import exersite.workout.Config.CurrentUser;
import exersite.workout.Controller.Dtos.ChatMessageDto;
import exersite.workout.Controller.Dtos.ChatRoomDto;
import exersite.workout.Controller.Forms.ChatRoomForm;
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
        List<ChatRoomDto> chatRoomDtos = chatService.findAllChatRoomByDto(member);
        model.addAttribute("chatRoomDtos", chatRoomDtos);
        return "chat/chatRoomList";
    }

    /**
     * 이미 속해있는 채팅방으로 이동
     * model : 이전 채팅 기록 + roomId
     * */
    @GetMapping("/{roomId}")
    public String openChatRoom(@PathVariable("roomId") Long roomId, @CurrentUser Member member,
                               Model model) {
        List<ChatMessageDto> chatMessageDtos = chatService.findAllMessageByChatRoomIdDtos(roomId);
        model.addAttribute("roomId", roomId);
        model.addAttribute("userNickname", member.getNickname());
        model.addAttribute("chatMessageDtos", chatMessageDtos);
        return "chat/chatRoom";
    }

    /**
     * 새로운 채팅방 생성 페이지로 연결
     * */
    @GetMapping("/chatRoom/new/{otherUserId}")
    public String createChatRoom(@PathVariable("otherUserId") Long otherUserId, @CurrentUser Member member,
                                 Model model) {
        model.addAttribute("chatRoomForm", new ChatRoomForm());
        model.addAttribute("otherUserId", otherUserId);
        return "chat/roomForm";
    }

    /**
     * 채팅방 생성 버튼
     * */
    @PostMapping("/chatRoom/new/{otherUserId}")
    public String createRoom(ChatRoomForm chatRoomForm, @PathVariable("otherUserId") Long otherUserId,
                             @CurrentUser Member member, Model model) {
        chatService.createChatRoomProcess(member, otherUserId, chatRoomForm);
        List<ChatRoomDto> chatRoomDtos = chatService.findAllChatRoomByDto(member);
        model.addAttribute("chatRoomDtos", chatRoomDtos);
        return "chat/chatRoomList";
    }
}
