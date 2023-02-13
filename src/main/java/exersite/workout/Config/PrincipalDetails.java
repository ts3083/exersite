package exersite.workout.Config;

import exersite.workout.Domain.Member.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * /login 요청이 오면 시큐리티가 낚아채서 로그인을 진행
 * 로그인 진행이 완료되면 시큐리티 session을 만둘어줌
 * session에 들어갈 수 있는 오브젝트는 Authentication 타입 객체
 * Authentication 객체 안에는 user 정보가 있어야 함. 그 user오브젝트 타입 객체는 UserDetails 타입 객체
 *
 * 다시 말해, 로그인 정보를 Security Session이 담고 있다
 * Security Session에 들어갈 수 있는 객체는 Authentication 객체
 * Authentication 객체를 저장할 때, 유저 정보는 UserDetails 타입 객체
 *
 * Security Session -> Authentication -> UserDetails(PrincipalDetails)
 */

@RequiredArgsConstructor
@Getter
public class PrincipalDetails implements UserDetails {

    private final Member member;

    // 해당 회원의 권한을 리턴하는 곳
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getNickname();
    }

    public String getLoginId() {
        return member.getLoginId();
    }

    public Long getId() {
        return member.getId();
    }

    public String getName() {
        return member.getName();
    }

    public String getCity() {
        return member.getAddress().getCity();
    }

    public String getStreet() {
        return member.getAddress().getStreet();
    }

    public String getZipcode() {
        return member.getAddress().getZipcode();
    }

    // 계정이 만료되었는가
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠겨있는가
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 계정의 사용기간이 지났는가
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        /**
         * 만약에 1년동안 회원이 로그인을 안하면 휴면 계정으로 하기 했다면
         * 현재시간 - 로그인시간 => 1년을 초과하면 return false;
         */
        return true;
    }
}
