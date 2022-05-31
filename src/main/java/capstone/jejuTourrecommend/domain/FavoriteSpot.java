package capstone.jejuTourrecommend.domain;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of={"id","count"})
public class FavoriteSpot {

    @Id @GeneratedValue
    @Column(name = "favorite_spot_id")
    private Long id;
    private int count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "favorite_id")
    private Favorite favorite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spot_id")
    private Spot spot;

    public FavoriteSpot(Favorite favorite) {

        if(favorite!=null){
            changeFavorite(favorite);
        }
    }

    private void changeFavorite(Favorite favorite) {
        this.favorite = favorite;
        favorite.getFavoriteSpotList().add(this);
    }

    public FavoriteSpot(Favorite favorite, Spot spot) {
        this.favorite = favorite;
        this.spot = spot;
    }

    public FavoriteSpot(Spot spot) {


        this.spot = spot;
    }
}













