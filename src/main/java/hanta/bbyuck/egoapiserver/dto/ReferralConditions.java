package hanta.bbyuck.egoapiserver.dto;

import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.domain.lol.enumset.LolPosition;
import hanta.bbyuck.egoapiserver.domain.lol.enumset.LolTier;
import lombok.Data;

/*
 * HANTA - Referral Conditions class
 *
 * @ description : 선호하는 상대 유저(추천 알고리즘) 조건을 걸기 위한 단순 Data Object 클래스
 *
 *
 * @      author : 강혁(bbyuck) (k941026h@naver.com)
 * @       since : 2020. 01. 01
 * @ last update : 2021. 02. 22
 *
 * <Copyright 2020. 한타. All rights reserved.>
 */

@Data
public class ReferralConditions {
    private User apiCaller;
    // 가능한 티어대
    private LolTier tier;
    // 유저에 맞는 포지션
    private LolPosition[] positions;
}