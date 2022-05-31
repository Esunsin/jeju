package capstone.jejuTourrecommend.domain;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of={"id","name"})
public class Favorite {


    @Id @GeneratedValue
    @Column(name = "favorite_id")
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "favorite")
    private List<FavoriteSpot> favoriteSpotList = new ArrayList<>();


    public Favorite(String name) {
        this.name = name;
    }

    public Favorite(String name, Member member) {
        this.name = name;
        if(member!=null){
            changeMember(member);
        }
    }

    private void changeMember(Member member) {
        this.member = member;
        member.getFavorites().add(this);
    }
}











