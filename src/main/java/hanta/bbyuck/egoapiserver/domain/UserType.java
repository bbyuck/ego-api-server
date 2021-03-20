package hanta.bbyuck.egoapiserver.domain;

import hanta.bbyuck.egoapiserver.domain.enumset.EgoTestVersion;
import hanta.bbyuck.egoapiserver.domain.enumset.Game;
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
public class UserType {

    @Id @GeneratedValue
    @Column(name = "user_type_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User user;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Column(name = "game", length = 45)
    @Enumerated(EnumType.STRING)
    private Game game;

    @Column(name = "ego_test_version")
    @Enumerated(EnumType.STRING)
    private EgoTestVersion version;

    @Column(name = "type", length = 45)
    private String type;

    /*
     * 편의 메서드
     */

    public void makeUserType(User user, Game game, EgoTestVersion version, String type) {
        this.user = user;
        this.game = game;
        this.updateTime = LocalDateTime.now();
        this.version = version;
        this.type = type;
    }
}