package exersite.workout.api;

import exersite.workout.Config.CurrentUser;
import exersite.workout.Config.PrincipalDetails;
import exersite.workout.Controller.Dtos.MemberDto;
import exersite.workout.Domain.Member.Member;
import exersite.workout.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/api/members")
    public List<MemberDto> profileData() {
        return memberService.findMemberDtos();
    }
}
