package exersite.workout.api;

import exersite.workout.Controller.Dtos.MemberDto;
import exersite.workout.Controller.Forms.MemberForm;
import exersite.workout.Domain.Member.Address;
import exersite.workout.Domain.Member.Member;
import exersite.workout.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController // @Controller + @ResponseBody -> JSON파일로 반환
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/api/members/new")
    public MemberDto apiSaveMember(@RequestBody @Valid MemberForm form) {
        // 비밀번호 인코딩
        String encodePassword = passwordEncoder.encode(form.getPassword());

        Member member = Member.createMember(form.getLoginId(),
                new Address(form.getCity(), form.getStreet(), form.getZipcode()),
                form.getName(), form.getNickname(), encodePassword);

        memberService.join(member); // 회원 등록
        return new MemberDto(member);
    }
}