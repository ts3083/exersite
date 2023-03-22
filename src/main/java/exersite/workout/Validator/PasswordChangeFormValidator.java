package exersite.workout.Validator;

import exersite.workout.Controller.Forms.PasswordChangeForm;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PasswordChangeFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return PasswordChangeForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PasswordChangeForm passwordChangeForm = (PasswordChangeForm) target;
        if(!passwordChangeForm.getNewPassword().equals(passwordChangeForm.getNewPasswordConfirm())) {
            // 입력한 패스워드가 다르면 에러
            errors.rejectValue("newPassword", "wrong.value", "새 비밀번호가 일치하지 않습니다");
        }
    }
}
