package hanta.bbyuck.egoapiserver.domain.lol;

import hanta.bbyuck.egoapiserver.domain.User;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

/*
 * HANTA - Lol Recommendation Refresh Entity class
 *
 * @ description : Lol Recommendation Refresh Entity 클래스 / E - R Diagram 참고해 연관관계 확인
 *                 오늘의 추천 유저 새로고침 횟수 저장을 위한 Entity
 *
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * @ 기획상 문제가 있지 않는 경우 절대 건드리지 말 것 @
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 *
 * @      author : 강혁(bbyuck) (k941026h@naver.com)
 * @       since : 2020. 01. 01
 * @ last update : 2021. 02. 22
 *
 * <Copyright 2020. 한타. All rights reserved.>
 */

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "opponent_id", referencedColumnName = "USER_ID")
    private User opponent;

    @Column(name = "refresh_datetime")
    private LocalDateTime refreshDatetime;

    /*
     * 유저 편의 메서드
     */

    public void makeRefresh(User apiCaller, User opponent) {
        this.apiCaller = apiCaller;
        this.opponent = opponent;
        refreshDatetime = LocalDateTime.now();
    }
}
