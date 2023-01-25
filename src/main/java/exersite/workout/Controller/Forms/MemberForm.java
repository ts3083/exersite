package exersite.workout.Controller.Forms;

import exersite.workout.Domain.Member.Address;
import exersite.workout.Domain.Member.Member;
import exersite.workout.Domain.Member.MemberStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Getter @Setter
public class MemberForm { // 회원가입 창에서 회원 정보를 입력받기 위한 Form
    /** 입력 받아야 할 정보
     * 1. 이메일 형식의 로그인 id
     * 2. 주소(도시, 도로명, 우편번호)
     * 3. 본인 실명
     * 4. 활동명
     * 5. 비밀번호
     * */
    @NotBlank(message = "필수 사항")
    private String loginId; // 로그인 아이디(이메일 형식)
    @NotBlank(message = "필수 사항")
    private String city; // 도시
    @NotBlank(message = "필수 사항")
    private String street; // 도로명
    @NotBlank(message = "필수 사항")
    private String zipcode; // 우편번호
    @NotBlank(message = "필수 사항")
    private String name; // 실명
    @NotBlank(message = "필수 사항")
    @Length(min = 3, max = 10)
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9]{3,10}$")
    private String nickname; // 활동명
    @NotBlank(message = "필수 사항")
    @Length(min = 8, max = 50)
    private String password; // 비밀번호

    public static MemberForm createMember(String loginId, Address address, String name,
                                          String nickname, String password) {
        MemberForm memberForm = new MemberForm();
        memberForm.setLoginId(loginId);
        memberForm.setCity(address.getCity());
        memberForm.setStreet(address.getStreet());
        memberForm.setZipcode(address.getZipcode());
        memberForm.setName(name);
        memberForm.setNickname(nickname);
        memberForm.setPassword(password);
        return memberForm;
    }
}
