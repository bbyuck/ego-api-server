package hanta.bbyuck.egoapiserver.domain;


import hanta.bbyuck.egoapiserver.domain.enumset.ExpStatus;
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
