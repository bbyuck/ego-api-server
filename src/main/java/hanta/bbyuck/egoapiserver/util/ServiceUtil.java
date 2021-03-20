package hanta.bbyuck.egoapiserver.util;

import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.domain.UserType;
import hanta.bbyuck.egoapiserver.domain.lol.LolProfileCard;
import hanta.bbyuck.egoapiserver.domain.lol.LolRecommendationRefresh;
import hanta.bbyuck.egoapiserver.response.lol.LolProfileCardResponseDto;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.*;

import static hanta.bbyuck.egoapiserver.util.Codes.*;

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

public class ServiceUtil {

    public static Map<Character, String> URL_Encoding_Map;

    public static LolProfileCard checkRecommendation(List<LolProfileCard> recommendations, List<LolRecommendationRefresh> refreshes) {
        LolProfileCard answer = null;
        Integer MAX_RECOMMENDATION_NUM = 3;

        for (LolProfileCard recommendation : recommendations) System.out.println ("id : " + recommendation.getId());

        if (refreshes.isEmpty()) answer = recommendations.get(0);
        else {
            for (LolProfileCard recommendation : recommendations) {
                for (LolRecommendationRefresh refresh : refreshes) {
//                    System.out.println("id : " + recommendation.getOwner().getId() + " / 활동 시각 : " + recommendation.getOwner().getLastActiveTime());
//                    System.out.println("추천받은 유저 : " + recommendation.getOwner() + " / 새로고침해서 만난사람 : " + refresh.getOpponent());
                    if (recommendation.getOwner() != refresh.getOpponent()) {
                        answer = recommendation;
                        break;
                    }
                }
                if(answer != null) break;
            }
        }


        if (answer == null) answer = recommendations.get(MAX_RECOMMENDATION_NUM);

        return answer;
    }
    @NotNull
    public static  LolProfileCardResponseDto fillLolProfileCardResponseDto(LolProfileCard findCard, Integer flag) {
        LolProfileCardResponseDto lolProfileCardDto = new LolProfileCardResponseDto();
        lolProfileCardDto.setProfileCardId(findCard.getId());
        lolProfileCardDto.setVoice(findCard.getVoice());
        if (flag.equals(ORIGINAL)) lolProfileCardDto.setSummonerName(findCard.getSummonerName());
        else if (flag.equals(PROCESSED)) lolProfileCardDto.setSummonerName(findCard.getSummonerName().charAt(0) + "***");
        lolProfileCardDto.setTier(findCard.getTier());
        lolProfileCardDto.setLastActiveTime(findCard.getOwner().getLastActiveTime());
        lolProfileCardDto.setTierLev(findCard.getTierLev());
        lolProfileCardDto.setLp(findCard.getLp());
        lolProfileCardDto.setChampion1(findCard.getChampion1());
        lolProfileCardDto.setChampion2(findCard.getChampion2());
        lolProfileCardDto.setChampion3(findCard.getChampion3());
        lolProfileCardDto.setTop(findCard.getTop());
        lolProfileCardDto.setJungle(findCard.getJungle());
        lolProfileCardDto.setMid(findCard.getMid());
        lolProfileCardDto.setAd(findCard.getAd());
        lolProfileCardDto.setSupport(findCard.getSupport());
        lolProfileCardDto.setMainLolPosition(findCard.getMainLolPosition());
        lolProfileCardDto.setMatchType(findCard.getMatchType());
        lolProfileCardDto.setFavoriteTier(findCard.getFavoriteTier());
        lolProfileCardDto.setFavoriteTop(findCard.getFavoriteTop());
        lolProfileCardDto.setFavoriteJungle(findCard.getFavoriteJungle());
        lolProfileCardDto.setFavoriteMid(findCard.getFavoriteMid());
        lolProfileCardDto.setFavoriteAd(findCard.getFavoriteAd());
        lolProfileCardDto.setFavoriteSupport(findCard.getFavoriteSupport());
        lolProfileCardDto.setGameType(findCard.getGameType());

        return lolProfileCardDto;
    }

    public static void addCardToDeck(List<LolProfileCardResponseDto> cards, LolProfileCard card) {
        LolProfileCardResponseDto processedCard = fillLolProfileCardResponseDto(card, PROCESSED);
        cards.add(processedCard);
    }

}
