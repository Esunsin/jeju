package capstone.jejuTourrecommend.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of={"id","rankAverage","viewScore","priceScore","facilityScore","surroundScore"})
public class Score {

    @Id @GeneratedValue
    @Column(name = "score_id")
    private Long id;

    private Double viewScore;
    private Double priceScore;
    private Double facilityScore;
    private Double surroundScore;

    private Double viewRank;
    private Double priceRank;
    private Double facilityRank;
    private Double surroundRank;

    private Double rankAverage;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "score")
    private Spot spot;

    public Score(Double viewScore, Double priceScore, Double facilityScore,
                 Double surroundScore, Double viewRank, Double priceRank,
                 Double facilityRank, Double surroundRank, Double rankAverage) {
        this.viewScore = viewScore;
        this.priceScore = priceScore;
        this.facilityScore = facilityScore;
        this.surroundScore = surroundScore;
        this.viewRank = viewRank;
        this.priceRank = priceRank;
        this.facilityRank = facilityRank;
        this.surroundRank = surroundRank;
        this.rankAverage = rankAverage;
    }
}
















