package capstone.jejuTourrecommend.domain;


import capstone.jejuTourrecommend.domain.baseEntity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of={"id","username","email","password"})
public class Member extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String username;
    private String email;
    private String password;

    //멤버 권한 정함//나 권한은 한사람당 한명씩 같게 함
    private String role;

    //refreshToken;
    private String refreshToken;


    @OneToMany(mappedBy = "member")
    private List<Favorite> favorites = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<MemberSpot> memberSpotList = new ArrayList<>();

    public Member(String username, String email) {
        this.username = username;
        this.email = email;
    }


    public Member(String email) {
        this.email = email;
    }

    public Member(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Member(String username, String email, String password, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}


















