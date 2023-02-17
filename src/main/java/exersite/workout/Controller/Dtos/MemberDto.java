package exersite.workout.Controller.Dtos;

import exersite.workout.Config.PrincipalDetails;
import exersite.workout.Domain.Member.Member;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class MemberDto {

    private Long id;
    private String loginId;
    private String name;
    private String nickname;
    private String city;
    private String street;
    private String zipcode;

    public MemberDto() {}

    public MemberDto(Member member) {
        this.id = member.getId();
        this.loginId = member.getLoginId();
        this.name = member.getName();
        this.nickname = member.getNickname();
        this.city = member.getAddress().getCity();
        this.street = member.getAddress().getStreet();
        this.zipcode = member.getAddress().getZipcode();
    }
}
