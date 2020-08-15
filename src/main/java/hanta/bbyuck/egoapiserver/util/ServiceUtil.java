package hanta.bbyuck.egoapiserver.util;

import hanta.bbyuck.egoapiserver.domain.lol.LolDuoProfileCard;
import hanta.bbyuck.egoapiserver.response.lol.LolProcessedDuoProfileCard;
import hanta.bbyuck.egoapiserver.response.lol.LolRequestDuoProfileCard;

import java.time.LocalDateTime;
import java.util.List;

public class ServiceUtil {

    public static void addCardToProcessedDeck(List<LolProcessedDuoProfileCard> cards, LolDuoProfileCard card) {
        LolProcessedDuoProfileCard processedCard = new LolProcessedDuoProfileCard();
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
        cards.add(processedCard);
    }

    public static void addCardToRequestDeck(List<LolRequestDuoProfileCard> cards, LolDuoProfileCard card, LocalDateTime requestTime) {
        LolRequestDuoProfileCard processedCard = new LolRequestDuoProfileCard();
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
