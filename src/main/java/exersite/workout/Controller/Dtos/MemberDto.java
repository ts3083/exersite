package exersite.workout.Controller.Dtos;

import exersite.workout.Config.PrincipalDetails;
import exersite.workout.Domain.Member;
import lombok.Data;

@Data
public class MemberDto {

    private Long id;
    private String loginId;
    private String name;
    private String nickname;
    private String city;
    private String street;
    private String zipcode;

    public MemberDto(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.nickname = member.getNickname();
        this.city = member.getAddress().getCity();
        this.street = member.getAddress().getStreet();
        this.zipcode = member.getAddress().getZipcode();
    }

    public MemberDto(PrincipalDetails principalDetails) {
        this.id = principalDetails.getId();
        this.loginId = principalDetails.getUsername();
        this.name = principalDetails.getName();
        this.nickname = principalDetails.getNickname();
        this.city = principalDetails.getCity();
        this.street = principalDetails.getStreet();
        this.zipcode = principalDetails.getZipcode();
    }
}
