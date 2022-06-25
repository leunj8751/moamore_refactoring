package moa.moamore.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;

@Getter @Setter
public class MemberDTO {

    private String memberId;

    private String password;

    private String nickname;


}
