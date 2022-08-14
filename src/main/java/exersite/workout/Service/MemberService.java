package exersite.workout.Service;

import exersite.workout.Controller.Dtos.MemberUpdateDto;
import exersite.workout.Domain.Member.Address;
import exersite.workout.Domain.Member.Member;
import exersite.workout.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 단순 조회의 경우 readOnly=true
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    //회원 가입(등록)
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검사
        memberRepository.save(member);
        return member.getId();
    }

    // 중복 회원 검사 메서드
    private void validateDuplicateMember(Member member) {
        List<Member> members = memberRepository.findMembersByLoginId(member.getLoginId());
        if (!members.isEmpty()) { // 이미 같은 로그인아이디를 갖는 회원이라면 중복회원 처리
            throw new IllegalStateException("이미 존재하는 회원입니다");
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

    // 회원 정보 업데이트
    public void updateMember(MemberUpdateDto memberUpdateDto) {
        Member member = memberRepository.findOne(memberUpdateDto.getId());
        member.setName(memberUpdateDto.getName());
        member.setNickname(memberUpdateDto.getNickname());
        member.setAddress(new Address(memberUpdateDto.getCity(),
                memberUpdateDto.getStreet(), memberUpdateDto.getZipcode()));
    }
}
