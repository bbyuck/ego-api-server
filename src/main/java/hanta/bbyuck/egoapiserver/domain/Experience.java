package hanta.bbyuck.egoapiserver.domain;


import hanta.bbyuck.egoapiserver.domain.enumset.ExpStatus;
import hanta.bbyuck.egoapiserver.domain.enumset.Game;
import hanta.bbyuck.egoapiserver.domain.enumset.GameType;
import hanta.bbyuck.egoapiserver.domain.enumset.MatchType;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

/*
 * HANTA - Experience Entity class
 *
 * @ description : Experience Entity 클래스 / E - R Diagram 참고해 연관관계 확인
 *                 유저에게 제공될 경험치를 모델링한 Entity
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
@Table(name = "experience")
@SequenceGenerator(
        name = "exp_seq_generator",
        sequenceName = "exp_sequence"
)
public class Experience {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "exp_seq_generator"
    )
    @Column(name = "experience_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column(name = "game")
    @Enumerated(EnumType.STRING)
    private Game game;

    @Column(name = "match_type")
    @Enumerated(EnumType.STRING)
    private MatchType matchType;


    @Column(name = "exp_status")
    @Enumerated(EnumType.STRING)
    private ExpStatus expStatus;


    @Column(name = "get_date")
    private LocalDateTime getDate;


    /*
     * 유저 편의 메서드
     */

    public void makeExp(User user, Game game, MatchType matchType, ExpStatus expStatus) {
        this.owner = user;
        this.game = game;
        this.matchType = matchType;
        this.expStatus = expStatus;
        this.getDate = LocalDateTime.now();
    }
}
