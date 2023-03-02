package exersite.workout.Validator;

import exersite.workout.Controller.Forms.ChatRoomForm;
import exersite.workout.Repository.Chat.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class ChatRoomFormValidator implements Validator {

    private final ChatRoomRepository chatRoomRepository;

    @Override
    public boolean supports(Class<?> clazz) { // 어떤 타입의 validator를 검증할 것인지
        return clazz.isAssignableFrom(ChatRoomForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) { // chatRoom이 중복되는지 검사
        ChatRoomForm chatRoomForm = (ChatRoomForm) target;

        if (chatRoomRepository.existsByRoomName(chatRoomForm.getRoomName())) {
            errors.rejectValue("roomName", "invalid.roomName",
                    new Object[]{chatRoomForm.getRoomName()}, "이미 사용중인 채팅방입니다.");
        }
    }
}
