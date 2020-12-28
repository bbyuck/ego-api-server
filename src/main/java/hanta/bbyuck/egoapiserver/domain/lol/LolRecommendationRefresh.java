package hanta.bbyuck.egoapiserver.domain.lol;

import hanta.bbyuck.egoapiserver.domain.User;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "lol_recommendation_refresh")
@SequenceGenerator(
        name = "lol_recommendation_refresh_seq_generator",
        sequenceName = "lol_recommendation_refresh_sequence"
)
public class LolRecommendationRefresh {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "lol_recommendation_refresh_seq_generator"
    )
    @Column(name = "LOL_RECOMMENDATION_REFRESH")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caller_id", referencedColumnName = "USER_ID")
    private User apiCaller;

    @Column(name = "refresh_datetime")
    private LocalDateTime refreshDatetime;

    /*
     * 유저 편의 메서드
     */

    public void makeRefresh(User apiCaller) {
        this.apiCaller = apiCaller;
        refreshDatetime = LocalDateTime.now();
    }
}
