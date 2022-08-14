package exersite.workout.Controller.Dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MemberUpdateDto {
    @NotNull
    private Long id;
    private String loginId;
    private String name;
    private String nickname;
    private String city;
    private String street;
    private String zipcode;

    public MemberUpdateDto(Long id) {
        this.id = id;
    }
}
