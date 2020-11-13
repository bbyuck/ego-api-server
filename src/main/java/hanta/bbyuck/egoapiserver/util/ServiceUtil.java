package hanta.bbyuck.egoapiserver.util;

import hanta.bbyuck.egoapiserver.domain.UserType;
import hanta.bbyuck.egoapiserver.domain.lol.LolProfileCard;
import hanta.bbyuck.egoapiserver.response.lol.LolProcessedProfileCard;
import hanta.bbyuck.egoapiserver.response.lol.LolRequestProfileCard;

import java.time.LocalDateTime;
import java.util.List;

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

    public static void addCardToRequestDeck(List<LolRequestProfileCard> cards, LolProfileCard card, LocalDateTime requestTime) {
        LolRequestProfileCard processedCard = new LolRequestProfileCard();
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
        processedCard.setRequestTime(requestTime);
        cards.add(processedCard);
    }

}
