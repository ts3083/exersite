package exersite.workout.Service;

import exersite.workout.Controller.Forms.MemberForm;
import exersite.workout.Domain.Member.Address;
import exersite.workout.Domain.Member.Member;
import exersite.workout.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 단순 조회의 경우 readOnly=true
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

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // id로 회원 조회
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
