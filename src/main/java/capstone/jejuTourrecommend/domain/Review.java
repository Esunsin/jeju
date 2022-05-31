package capstone.jejuTourrecommend.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of={"id","content"})
public class Review {

    @Id @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spot_id")
    private Spot spot;


    public Review(String content, Spot spot) {
        this.content = content;
        if (spot != null) {
            changeSpot(spot);
        }
    }

    private void changeSpot(Spot spot) {
        this.spot = spot;
        spot.getReviews().add(this);
    }
}


























