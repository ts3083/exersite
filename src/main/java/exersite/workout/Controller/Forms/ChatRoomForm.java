package exersite.workout.Controller.Forms;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class ChatRoomForm {

    @NotBlank(message = "필수 사항")
    private String roomName;
}
