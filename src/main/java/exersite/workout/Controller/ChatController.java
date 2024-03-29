package exersite.workout.Controller;

import exersite.workout.Config.CurrentUser;
import exersite.workout.Controller.Dtos.ChatMessageDto;
import exersite.workout.Controller.Dtos.ChatRoomDto;
import exersite.workout.Controller.Forms.ChatRoomForm;
import exersite.workout.Domain.Member.Member;
import exersite.workout.Service.ChatService;
import exersite.workout.Validator.ChatRoomFormValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final ChatRoomFormValidator chatRoomFormValidator;

    @InitBinder("chatRoomForm")
    public void chatRoomFormInitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(chatRoomFormValidator);
    }

    /**
     * 내 프로필에서 내가 참여하고 있는 채팅방 목록 보여주기
     * 로그인한 Member와 chatMembers를 통해 CurrentUser가 참여하고 있는 chatRoom 목록 전달
     * */
    @GetMapping("/myChatRooms")
    public String myChatRooms(@CurrentUser Member member, Model model) {
        List<ChatRoomDto> chatRoomDtos = chatService.findAllChatRoomByDto(member);
        model.addAttribute("chatRoomDtos", chatRoomDtos);
        return "chat/chatRoomList";
    }

    /**
     * 모든 채팅방 목록 페이지
     * */
    @GetMapping("/chatRooms")
    public String chatRooms(Model model) {
        List<ChatRoomDto> chatRoomDtos = chatService.findAllChatRoomDtos();
        model.addAttribute("chatRoomDtos", chatRoomDtos);
        return "chat/chatRoomList";
    }

    /**
     * 채팅방으로 이동
     * model : 이전 채팅 기록 + roomId
     * */
    @GetMapping("/{roomId}")
    public String openChatRoom(@PathVariable("roomId") Long roomId, @CurrentUser Member member,
                               Model model) {
        chatService.checkChatMembers(roomId, member); // chatMembers에 존재하는지 검증 필요 + 없다면 저장
        List<ChatMessageDto> chatMessageDtos = chatService.findAllMessageByChatRoomIdDtos(roomId);
        model.addAttribute("roomId", roomId);
        model.addAttribute("userNickname", member.getNickname());
        model.addAttribute("chatMessageDtos", chatMessageDtos);
        return "chat/chatRoom";
    }

    /**
     * 새로운 채팅방 생성 페이지로 연결
     * */
    @GetMapping("/chatRoom/new")
    public String createChatRoom(Model model) {
        model.addAttribute("chatRoomForm", new ChatRoomForm());
        return "chat/roomForm";
    }

    /**
     * 채팅방 생성 버튼
     * chatRoom, chatMembers에 본인 저장하기
     * */
    @PostMapping("/chatRoom/new")
    public String createRoom(@Valid ChatRoomForm chatRoomForm, Errors errors,
                             @CurrentUser Member member, Model model) {
        if (errors.hasErrors()) {
            return "chat/roomForm";
        }
        chatService.createChatRoomProcess(member, chatRoomForm);
        List<ChatRoomDto> chatRoomDtos = chatService.findAllChatRoomByDto(member);
        model.addAttribute("chatRoomDtos", chatRoomDtos);
        return "chat/chatRoomList";
    }
}
