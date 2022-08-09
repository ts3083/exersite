package exersite.workout.Controller.Forms;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberForm { // 회원가입 창에서 회원 정보를 입력받기 위한 Form
    /** 입력 받아야 할 정보
     * 1. 이메일 형식의 로그인 id
     * 2. 주소(도시, 도로명, 우편번호)
     * 3. 본인 실명
     * 4. 활동명
     * 5. 비밀번호
     * */
    @NotEmpty(message = "필수 사항")
    private String loginId; // 로그인 아이디(이메일 형식)
    @NotEmpty(message = "필수 사항")
    private String city; // 도시
    @NotEmpty(message = "필수 사항")
    private String street; // 도로명
    @NotEmpty(message = "필수 사항")
    private String zipcode; // 우편번호
    @NotEmpty(message = "필수 사항")
    private String name; // 실명
    @NotEmpty(message = "필수 사항")
    private String nickname; // 활동명
    @NotEmpty(message = "필수 사항")
    private String password; // 비밀번호
}
