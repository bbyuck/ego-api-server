package hanta.bbyuck.egoapiserver.domain;

import hanta.bbyuck.egoapiserver.domain.enumset.Game;
import hanta.bbyuck.egoapiserver.domain.enumset.GameType;
import hanta.bbyuck.egoapiserver.domain.enumset.MatchType;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

/*
 * <pre>
 * Copyright (c) 2020 HANTA
 * All rights reserved.
 *
 * This software is the proprietary information of HANTA
 * </pre>
 *
 * @ author 강혁(bbyuck) (k941026h@naver.com)
 * @ since  2020. 01. 01
 *
 * @History
 * <pre>
 * -----------------------------------------------------
 * 2020.01.01
 * bbyuck (k941026h@naver.com) 최초작성
 * -----------------------------------------------------
 * </pre>
 */

@Entity
@Getter
@Table(name = "esti_score")
@SequenceGenerator(
        name = "esti_score_seq_generator",
        sequenceName = "esti_score_sequence"
)
public class EgoScore {

    @Id @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "esti_score_seq_generator"
    )
    @Column(name = "ESTI_SCORE_ID")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GIVER_ID", referencedColumnName = "USER_ID")
    private User giver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECEIVER_ID", referencedColumnName = "USER_ID")
    private User receiver;


    private Game game;
    private MatchType matchType;
    private Integer point;
    private LocalDateTime evaluationTime;

    /*
     * 유저 편의 메서드
     */

    public void makeScore(User giver, User receiver, Game game, MatchType matchType, Integer point) {
        this.giver = giver;
        this.receiver = receiver;
        this.game = game;
        this.matchType = matchType;
        this.point = point;
        this.evaluationTime = LocalDateTime.now();
    }
}
