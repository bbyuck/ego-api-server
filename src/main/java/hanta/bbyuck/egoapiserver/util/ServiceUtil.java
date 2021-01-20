package hanta.bbyuck.egoapiserver.util;


import hanta.bbyuck.egoapiserver.domain.UserType;
import hanta.bbyuck.egoapiserver.domain.lol.LolProfileCard;
import hanta.bbyuck.egoapiserver.domain.lol.LolRecommendationRefresh;
import hanta.bbyuck.egoapiserver.response.lol.LolProcessedProfileCard;
import hanta.bbyuck.egoapiserver.response.lol.LolRequestProfileCard;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.*;

public class ServiceUtil {

    public static void addCardToProcessedDeck(List<LolProcessedProfileCard> cards, LolProfileCard card, UserType userType) {
        LolProcessedProfileCard processedCard = new LolProcessedProfileCard();
        processedCard.setProfileCardId(card.getId());
        processedCard.setVoice(card.getVoice());
        processedCard.setLimitedSummonerName(card.getSummonerName().substring(0, 1) + "***");
        processedCard.setTier(card.getTier());
        processedCard.setTierLev(card.getTierLev());
        processedCard.setLp(card.getLp());
        processedCard.setChampion1(card.getChampion1());
        processedCard.setChampion2(card.getChampion2());
        processedCard.setChampion3(card.getChampion3());
        processedCard.setTop(card.getTop());
        processedCard.setJungle(card.getJungle());
        processedCard.setMid(card.getMid());
        processedCard.setAd(card.getAd());
        processedCard.setSupport(card.getSupport());
        processedCard.setMainLolPosition(card.getMainLolPosition());
        processedCard.setLastActiveTime(card.getOwner().getLastActiveTime());
        processedCard.setMatchType(card.getMatchType());
        processedCard.setFavoriteTier(card.getFavoriteTier());
        processedCard.setFavoriteTop(card.getFavoriteTop());
        processedCard.setFavoriteJungle(card.getFavoriteJungle());
        processedCard.setFavoriteMid(card.getFavoriteMid());
        processedCard.setFavoriteAd(card.getFavoriteAd());
        processedCard.setFavoriteSupport(card.getFavoriteSupport());

        if (userType == null) {
            processedCard.setUserType(null);
            processedCard.setEgoTestVersion(null);
        }
        else {
            processedCard.setUserType(userType.getType());
            processedCard.setEgoTestVersion(userType.getVersion());
        }
        cards.add(processedCard);
    }

    public static void addCardToRequestDeck(List<LolProcessedProfileCard> cards, LolProfileCard card, LocalDateTime requestTime) {
        LolProcessedProfileCard processedCard = new LolProcessedProfileCard();
        processedCard.setProfileCardId(card.getId());
        processedCard.setLastActiveTime(card.getOwner().getLastActiveTime());
        processedCard.setVoice(card.getVoice());
        processedCard.setLimitedSummonerName(card.getSummonerName().substring(0, 1) + "***");
        processedCard.setTier(card.getTier());
        processedCard.setTierLev(card.getTierLev());
        processedCard.setLp(card.getLp());
        processedCard.setChampion1(card.getChampion1());
        processedCard.setChampion2(card.getChampion2());
        processedCard.setChampion3(card.getChampion3());
        processedCard.setTop(card.getTop());
        processedCard.setJungle(card.getJungle());
        processedCard.setMid(card.getMid());
        processedCard.setAd(card.getAd());
        processedCard.setSupport(card.getSupport());
        processedCard.setMainLolPosition(card.getMainLolPosition());
        cards.add(processedCard);
    }

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
}
