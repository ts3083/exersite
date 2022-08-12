package exersite.workout.Config;

import exersite.workout.Domain.Member.Member;
import exersite.workout.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 규칙
 * 시큐리티 설정에서 loginProcessingUrl("/login")
 * /login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC되어 있는 loadUserByUsername 함수가 실행
 */
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    // 시큐리티 session(내부 Authentication(내부 UserDetails))
    @Override
    public UserDetails loadUserByUsername(String login_email_id) throws UsernameNotFoundException {
        // login 아이디로 회원 찾기
        Member member = memberRepository.findByLoginId(login_email_id);
        if (member == null) {
            throw new IllegalStateException(login_email_id);
        }
        return new PrincipalDetails(member);
    }
}
