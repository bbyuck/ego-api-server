package hanta.bbyuck.egoapiserver.response.lol;

import hanta.bbyuck.egoapiserver.domain.lol.enumset.LolMatchingStatus;
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
public class LolMatchingResponseDto {
    private Long matchId;
    private LocalDateTime matchStartTime;

    private LolMatchingStatus matchingStatus;

    private Long opponentProfileCardId;
    private Boolean opponentVoice;
    private String opponentSummonerName;
    private LolTier opponentTier;
    private Integer opponentTierLev;
    private Integer opponentLp;
    private String opponentChampion1;
    private String opponentChampion2;
    private String opponentChampion3;
    private Boolean opponentTop;
    private Boolean opponentJungle;
    private Boolean opponentMid;
    private Boolean opponentAd;
    private Boolean opponentSupport;
    private LolPosition opponentMainLolPosition;
    private LocalDateTime opponentLastActiveTime;
}
