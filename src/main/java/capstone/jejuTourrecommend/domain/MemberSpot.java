package capstone.jejuTourrecommend.domain;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id","score","member","spot"})
public class MemberSpot {


    @Id @GeneratedValue
    @Column(name = "member_spot_id")
    private Long id;

    private Double score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spot_id")
    private Spot spot;

    public MemberSpot(Double score) {
        this.score = score;
    }

    public MemberSpot(Double score, Member member, Spot spot) {
        this.score = score;
        this.member = member;
        this.spot = spot;
    }
}

























