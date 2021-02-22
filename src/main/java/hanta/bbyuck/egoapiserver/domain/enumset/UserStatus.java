package hanta.bbyuck.egoapiserver.domain.enumset;

/*
 * HANTA - User Status class
 *
 * @ description : 유저의 상태 관련 Enum class. State Transition Diagram 함께 볼 것.
 *                 서비스되는 게임 추가시 여기에 유저 상태 추가.
 *
 * @      author : 강혁(bbyuck) (k941026h@naver.com)
 * @       since : 2020. 01. 01
 * @ last update : 2021. 02. 22
 *
 * <Copyright 2020. 한타. All rights reserved.>
 */

public enum UserStatus {
    ACTIVE, LOL_DUO_MATCHING_READY, LOL_DUO_MATCHING, LOL_DUO_MATCHING_FINISH
}
