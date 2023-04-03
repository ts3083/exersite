package exersite.workout.Service;

import exersite.workout.Config.PrincipalDetails;
import exersite.workout.Controller.Dtos.MemberDto;
import exersite.workout.Controller.Forms.MemberForm;
import exersite.workout.Domain.Member.Address;
import exersite.workout.Domain.Member.Member;
import exersite.workout.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void processNewMember(MemberForm memberForm) {
        validateDuplicateMember(memberForm); // 중복 회원 검사
        Long newMemberId = join(memberForm);
    }

    //회원 가입(등록)
    @Transactional
    public Long join(MemberForm memberForm) {
        // 비밀번호 인코딩
        String encodePassword = passwordEncoder.encode(memberForm.getPassword());

        Member member = Member.createMember(memberForm.getLoginId(),
                new Address(memberForm.getCity(), memberForm.getStreet(), memberForm.getZipcode()),
                memberForm.getName(), memberForm.getNickname(), encodePassword);
        memberRepository.save(member);

        return member.getId();
    }

    // 중복 회원 검사 메서드
    private void validateDuplicateMember(MemberForm memberForm) {
        if(!memberRepository.checkEmptyByemail(memberForm.getLoginId())) {
            throw new IllegalStateException("이미 사용 중인 이메일입니다");
        }
        if(!memberRepository.checkEmptyByNickname(memberForm.getNickname())) {
            throw new IllegalStateException("이미 사용 중인 닉네임입니다");
        }
    }

    // id로 회원 조회
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    // 회원 전체 조회 -> Dto로 변환
    public List<MemberDto> findMemberDtos() {
        return memberRepository.findAll().stream()
                .map(member -> new MemberDto(member))
                .collect(Collectors.toList());
    }

    // 프로필에서 내 정보 수정
    /*@Transactional
    public void updateMember(Long memberId, MemberDto memberDto) {
        Member member = memberRepository.findOne(memberId);
        member.setName(memberDto.getName());
        member.setNickname(memberDto.getNickname());
        member.setLoginId(memberDto.getLoginId());
        member.setAddress(new Address(memberDto.getCity(), memberDto.getStreet(), memberDto.getZipcode()));

        // 내 정보를 변경하면 다시 로그인하여 authentication을 재설정
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                new PrincipalDetails(member),
                member.getPassword());
        SecurityContextHolder.getContext().setAuthentication(token);
    }*/

    // 프로필에서 내 정보 수정 - 다시 로그인하지 않고 merge하는 방법
    @Transactional
    public void updateMember(Member member, MemberDto memberDto) {
        member.setName(memberDto.getName());
        member.setNickname(memberDto.getNickname());
        member.setLoginId(memberDto.getLoginId());
        member.setAddress(new Address(memberDto.getCity(), memberDto.getStreet(), memberDto.getZipcode()));
        memberRepository.save(member);
    }

    public MemberDto profile(String nickname) {
        Member member = memberRepository.findByNickname(nickname);
        if (member == null) {
            throw new IllegalArgumentException("회원을 찾을 수 없습니다.");
        }
        return new MemberDto(member);
    }

    @Transactional
    public void updatePassword(Member member, String newPassword) {
        member.setPassword(passwordEncoder.encode(newPassword));
        memberRepository.save(member);
    }
}
