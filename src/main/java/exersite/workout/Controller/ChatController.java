package exersite.workout.Controller;

import exersite.workout.Config.CurrentUser;
import exersite.workout.Controller.Dtos.ChatRoomDto;
import exersite.workout.Domain.Member.Member;
import exersite.workout.Repository.ChatRoomRepository;
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
    private final ChatRoomRepository chatRoomRepository;

    @GetMapping("/chatRooms")
    public String chatRoom(@CurrentUser Member member, Model model) {
        // 현재 채팅중인 채팅방 리스트 전달
        List<ChatRoomDto> chatRoomByDto = chatService.findAllChatRoomByDto(member);
        model.addAttribute("chatRooms", chatRoomByDto);
        return "chat/chatRoomList";
    }

    /*@GetMapping("/chatRoom/{roomId}")
    public String room(@PathVariable String roomId, Model model) {
        // 특정 채팅방으로 이동
        // 이전까지의 chatMessage 내용을 가져와서 전달
    }

    @PostMapping("/chatRoom/new/{nickname}")
    public String newRoom(@CurrentUser Member member,String nickname, Model model) {
        // 특정 사용자 프로필에서 채팅하기 버튼을 클릭
        // 이미 만들어졌는지 확인
        // 사용자들의 이름으로 채팅방 이름을 만들고 채팅방으로 이동
    }*/
}
