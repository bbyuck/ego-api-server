package hanta.bbyuck.egoapiserver.response.lol;

import hanta.bbyuck.egoapiserver.domain.UserType;
import hanta.bbyuck.egoapiserver.domain.enumset.EgoTestVersion;
import hanta.bbyuck.egoapiserver.domain.enumset.GameType;
import hanta.bbyuck.egoapiserver.domain.enumset.MatchType;
import hanta.bbyuck.egoapiserver.domain.lol.enumset.LolPosition;
import hanta.bbyuck.egoapiserver.domain.lol.enumset.LolTier;
import lombok.Data;

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

@Data
public class LolProfileCardResponseDto {
    private Long profileCardId;
    private LocalDateTime lastActiveTime;
    private LocalDateTime lastUpdateTime;
    private Boolean voice;
    private String summonerName;
    private LolTier tier;
    private Integer tierLev;
    private Integer lp;
    private String champion1;
    private String champion2;
    private String champion3;
    private Boolean top;
    private Boolean jungle;
    private Boolean mid;
    private Boolean ad;
    private Boolean support;
    private LolPosition mainLolPosition;
    private MatchType matchType;
    private GameType gameType;
    private UserType userType;
    private EgoTestVersion egoTestVersion;
    private LolTier favoriteTier;
    private Boolean favoriteTop;
    private Boolean favoriteJungle;
    private Boolean favoriteMid;
    private Boolean favoriteAd;
    private Boolean favoriteSupport;
}
