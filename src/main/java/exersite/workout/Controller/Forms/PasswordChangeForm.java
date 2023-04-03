package exersite.workout.Controller.Forms;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class PasswordChangeForm {

    @NotBlank(message = "필수 사항")
    @Length(min = 8, max = 50)
    private String newPassword;

    @NotBlank(message = "필수 사항")
    @Length(min = 8, max = 50)
    private String newPasswordConfirm;
}
