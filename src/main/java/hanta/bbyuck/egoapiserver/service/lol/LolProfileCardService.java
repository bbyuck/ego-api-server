package hanta.bbyuck.egoapiserver.service.lol;

import hanta.bbyuck.egoapiserver.domain.UserType;
import hanta.bbyuck.egoapiserver.domain.enumset.UserStatus;
import hanta.bbyuck.egoapiserver.domain.lol.LolRecommendationRefresh;
import hanta.bbyuck.egoapiserver.domain.lol.LolRequest;
import hanta.bbyuck.egoapiserver.domain.lol.enumset.LolTier;
import hanta.bbyuck.egoapiserver.dto.ReferralConditions;
import hanta.bbyuck.egoapiserver.exception.BadMatchRequestException;
import hanta.bbyuck.egoapiserver.exception.UpdateFailureException;
import hanta.bbyuck.egoapiserver.repository.UserTypeRepository;
import hanta.bbyuck.egoapiserver.repository.lol.LolRecommendationRefreshRepository;
import hanta.bbyuck.egoapiserver.repository.lol.LolRequestRepository;
import hanta.bbyuck.egoapiserver.response.lol.LolProcessedProfileCard;
import hanta.bbyuck.egoapiserver.response.lol.LolProcessedProfileCardDeck;
import hanta.bbyuck.egoapiserver.domain.lol.LolProfileCard;
import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.exception.lol.AlreadyOwnProfileCardException;
import hanta.bbyuck.egoapiserver.exception.lol.DuplicateSummonerNameException;
import hanta.bbyuck.egoapiserver.exception.lol.LolDuoProfileCardNotExistException;
import hanta.bbyuck.egoapiserver.repository.lol.LolProfileCardRepository;
import hanta.bbyuck.egoapiserver.repository.UserRepository;
import hanta.bbyuck.egoapiserver.request.lol.LolMatchDeckRequestDto;
import hanta.bbyuck.egoapiserver.request.lol.LolProfileCardMakeRequestDto;
import hanta.bbyuck.egoapiserver.request.lol.LolProfileCardRequestDto;
import hanta.bbyuck.egoapiserver.request.lol.LolProfileCardUpdateRequestDto;
import hanta.bbyuck.egoapiserver.response.lol.LolProfileCardResponseDto;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static hanta.bbyuck.egoapiserver.util.ClientVersionManager.*;
import static hanta.bbyuck.egoapiserver.util.ServiceUtil.addCardToProcessedDeck;
import static hanta.bbyuck.egoapiserver.util.ServiceUtil.checkRecommendation;

@Service
@RequiredArgsConstructor
public class LolProfileCardService {
    private final LolProfileCardRepository lolProfileCardRepository;
    private final UserRepository userRepository;
    private final UserTypeRepository userTypeRepository;
    private final LolRequestRepository lolRequestRepository;
    private final LolRecommendationRefreshRepository lolRecommendationRefreshRepository;


    private final Integer PROCESSED = 1;
    private final Integer ORIGINAL = 2;

    private LolProfileCard makeNewDuoProfileCard(User apiCaller) throws  NoResultException {
        LolProfileCard newProfileCard = new LolProfileCard();
        newProfileCard.updateFavorite(LolTier.NONE, false, false, false, false, false);
        return newProfileCard;
    }

    public void makeDuoProfileCard(LolProfileCardMakeRequestDto requestDto) throws NoResultException{
        checkClientVersion(requestDto.getClientVersion());

        User apiCaller = userRepository.find(requestDto.getGeneratedId());

        if(lolProfileCardRepository.isExist(apiCaller)) {
            throw new AlreadyOwnProfileCardException();
        }

        if (lolProfileCardRepository.isExistSummonerName(requestDto.getSummonerName())){
            throw new DuplicateSummonerNameException();
        }

        LolProfileCard lolProfileCard = makeNewDuoProfileCard(apiCaller);
        lolProfileCard.makeProfileCard(
                apiCaller,
                requestDto.getVoice(),
                requestDto.getSummonerName(),
                requestDto.getTier(),
                requestDto.getTierLev(),
                requestDto.getLp(),
                requestDto.getChampion1(),
                requestDto.getChampion2(),
                requestDto.getChampion3(),
                requestDto.getTop(),
                requestDto.getJungle(),
                requestDto.getMid(),
                requestDto.getAd(),
                requestDto.getSupport(),
                requestDto.getMainLolPosition(),
                requestDto.getGameType(),
                requestDto.getMatchType());
        lolProfileCardRepository.save(lolProfileCard);
    }

    public LolProfileCardResponseDto take(LolProfileCardRequestDto requestDto) {
        checkClientVersion(requestDto.getClientVersion());

        try {
            User apiCaller = userRepository.find(requestDto.getGeneratedId());
            LolProfileCard findCard = lolProfileCardRepository.find(apiCaller);

            LolProfileCardResponseDto responseDto = new LolProfileCardResponseDto();

            return fillLolProfileCardResponseDto(responseDto, apiCaller, findCard, ORIGINAL);
        } catch (NoResultException e) {
            throw new LolDuoProfileCardNotExistException();
        }
    }

    private LolProfileCardResponseDto fillLolProfileCardResponseDto(LolProfileCardResponseDto responseDto, User apiCaller, LolProfileCard findCard, Integer flag
    ) {
        UserType userType = null;
        if (userTypeRepository.exist(apiCaller)) userType = userTypeRepository.find(apiCaller);

        responseDto.setId(findCard.getId());
        responseDto.setVoice(findCard.getVoice());
        if (flag.equals(ORIGINAL)) responseDto.setSummonerName(findCard.getSummonerName());
        else if (flag.equals(PROCESSED)) responseDto.setSummonerName(findCard.getSummonerName().charAt(0) + "***");
        responseDto.setTier(findCard.getTier());
        responseDto.setLastActiveTime(findCard.getOwner().getLastActiveTime());
        responseDto.setTierLev(findCard.getTierLev());
        responseDto.setLp(findCard.getLp());
        responseDto.setChampion1(findCard.getChampion1());
        responseDto.setChampion2(findCard.getChampion2());
        responseDto.setChampion3(findCard.getChampion3());
        responseDto.setTop(findCard.getTop());
        responseDto.setJungle(findCard.getJungle());
        responseDto.setMid(findCard.getMid());
        responseDto.setAd(findCard.getAd());
        responseDto.setSupport(findCard.getSupport());
        responseDto.setMainLolPosition(findCard.getMainLolPosition());
        responseDto.setMatchType(findCard.getMatchType());
        responseDto.setFavoriteTier(findCard.getFavoriteTier());
        responseDto.setFavoriteTop(findCard.getFavoriteTop());
        responseDto.setFavoriteJungle(findCard.getFavoriteJungle());
        responseDto.setFavoriteMid(findCard.getFavoriteMid());
        responseDto.setFavoriteAd(findCard.getFavoriteAd());
        responseDto.setFavoriteSupport(findCard.getFavoriteSupport());
        responseDto.setGameType(findCard.getGameType());

        if (userType == null) {
            responseDto.setUserType(null);
            responseDto.setEgoTestVersion(null);
        }
        else {
            responseDto.setUserType(userType.getType());
            responseDto.setEgoTestVersion(userType.getVersion());
        }

        return responseDto;
    }

    public void updateMyCard(LolProfileCardUpdateRequestDto requestDto) {
        checkClientVersion(requestDto.getClientVersion());

        // 수정시 authCheck 할 것

        User owner = userRepository.find(requestDto.getGeneratedId());
        LolProfileCard lolProfileCard = lolProfileCardRepository.find(owner);

        lolProfileCardRepository.update(lolProfileCard, requestDto);
    }

    /*
     * takeDeck V2 -> 전체 뽑아오기 별로 성능 안좋음
     */
/*
    public LolDuoProfileCardDeck takeDeck(LolDuoMatchDeckRequestDto requestDto) {
        LolDuoProfileCardDeck deck = new LolDuoProfileCardDeck();
        User owner = userRepository.find(requestDto.getUserAuth());

        LolDuoProfileCard apiCallerCard = lolDuoProfileCardRepository.find(owner);
        List<LolDuoProfileCard> allCards = lolDuoProfileCardRepository.findAllOrderByActiveTime();

        List<ProcessedLolDuoProfileCard> cards = new ArrayList<>();
        int cardCount = 0;

        switch (apiCallerCard.getTier()) {
            case I:
            case B:
                // I, B, S
                for (LolDuoProfileCard card : allCards) {
                    if ((card.getTier() == LolTier.I || card.getTier() == LolTier.B || card.getTier() == LolTier.S)
                            && (card.getOwner().getStatus() == UserStatus.INACTIVE || card.getOwner().getStatus() == UserStatus.ACTIVE)) {
                        addCardToProcessedDeck(cards, card);
                        cardCount++;
                    }
                    if (cardCount == MAX_CARD_NUM) break;
                }
                break;
            case S:
                // I, B, S, G
                for (LolDuoProfileCard card : allCards) {
                    if ((card.getTier() == LolTier.I || card.getTier() == LolTier.B || card.getTier() == LolTier.S || card.getTier() == LolTier.G)
                            && (card.getOwner().getStatus() == UserStatus.INACTIVE || card.getOwner().getStatus() == UserStatus.ACTIVE)) {
                        addCardToProcessedDeck(cards, card);
                        cardCount++;
                    }
                    if (cardCount == MAX_CARD_NUM) break;
                }
                break;
            case G:
                // S, G, P
                for (LolDuoProfileCard card : allCards) {
                    if ((card.getTier() == LolTier.S || card.getTier() == LolTier.G || card.getTier() == LolTier.P)
                            && (card.getOwner().getStatus() == UserStatus.INACTIVE || card.getOwner().getStatus() == UserStatus.ACTIVE)) {
                        addCardToProcessedDeck(cards, card);
                        cardCount++;
                    }
                    if (cardCount == MAX_CARD_NUM) break;
                }
                break;
            case P:
                // G, P, if(P1 == D3, D4), if(P2 == D4)
                if(apiCallerCard.getTierLev() == 1) {
                    for (LolDuoProfileCard card : allCards) {
                        if ((card.getTier() == LolTier.G || card.getTier() == LolTier.P || ((card.getTier() == LolTier.D) && ((card.getTierLev().equals(3)) || card.getTierLev().equals(4))))
                                && (card.getOwner().getStatus() == UserStatus.INACTIVE || card.getOwner().getStatus() == UserStatus.ACTIVE)) {
                            addCardToProcessedDeck(cards, card);
                            cardCount++;
                        }
                        if (cardCount == MAX_CARD_NUM) break;
                    }
                }
                else if(apiCallerCard.getTierLev() == 2) {
                    for (LolDuoProfileCard card : allCards) {
                        if ((card.getTier() == LolTier.G || card.getTier() == LolTier.P || (card.getTier() == LolTier.D && card.getTierLev().equals(4)))
                                && (card.getOwner().getStatus() == UserStatus.INACTIVE || card.getOwner().getStatus() == UserStatus.ACTIVE)) {
                            addCardToProcessedDeck(cards, card);
                            cardCount++;
                        }
                        if (cardCount == MAX_CARD_NUM) break;
                    }
                }
                else {
                    for (LolDuoProfileCard card : allCards) {
                        if ((card.getTier() == LolTier.G || card.getTier() == LolTier.P)
                                && (card.getOwner().getStatus() == UserStatus.INACTIVE || card.getOwner().getStatus() == UserStatus.ACTIVE)) {
                            addCardToProcessedDeck(cards, card);
                            cardCount++;
                        }
                        if (cardCount == MAX_CARD_NUM) break;
                    }
                }
                break;
            case D:
                // if(D4 == P2, P1 - D4, D3, D2), if(D3 == P1, D4, D3, D2, D1), if(D2 == D4, D3, D2, D1, M1), if(D1 == D3, D2, D1, M, GM)
                if(apiCallerCard.getTierLev() == 4) {
                    for (LolDuoProfileCard card : allCards) {
                        if (((card.getTier() == LolTier.P && (card.getTierLev().equals(2) || card.getTierLev().equals(1))) || (card.getTier() == LolTier.D && !card.getTierLev().equals(1)))
                                && (card.getOwner().getStatus() == UserStatus.INACTIVE || card.getOwner().getStatus() == UserStatus.ACTIVE)) {
                            addCardToProcessedDeck(cards, card);
                            cardCount++;
                        }
                        if (cardCount == MAX_CARD_NUM) break;
                    }
                }
                else if (apiCallerCard.getTierLev() == 3) {
                    for (LolDuoProfileCard card : allCards) {
                        if (((card.getTier() == LolTier.P && card.getTierLev().equals(1)) || card.getTier() == LolTier.D)
                                && (card.getOwner().getStatus() == UserStatus.INACTIVE || card.getOwner().getStatus() == UserStatus.ACTIVE)) {
                            addCardToProcessedDeck(cards, card);
                            cardCount++;
                        }
                        if (cardCount == MAX_CARD_NUM) break;
                    }
                }
                else if (apiCallerCard.getTierLev() == 2) {
                    for (LolDuoProfileCard card : allCards) {
                        if ((card.getTier() == LolTier.D  || card.getTier() == LolTier.M)
                                && (card.getOwner().getStatus() == UserStatus.INACTIVE || card.getOwner().getStatus() == UserStatus.ACTIVE)) {
                            addCardToProcessedDeck(cards, card);
                            cardCount++;
                        }
                        if (cardCount == MAX_CARD_NUM) break;
                    }
                }
                else {
                    for (LolDuoProfileCard card : allCards) {
                        if (((card.getTier() == LolTier.D && !card.getTierLev().equals(4)) || card.getTier() == LolTier.M || card.getTier() == LolTier.GM)
                                && (card.getOwner().getStatus() == UserStatus.INACTIVE || card.getOwner().getStatus() == UserStatus.ACTIVE)) {
                            addCardToProcessedDeck(cards, card);
                            cardCount++;
                        }
                        if (cardCount == MAX_CARD_NUM) break;
                    }
                }
                break;
            case M:
                // D2, D1, M, GM
                for (LolDuoProfileCard card : allCards) {
                    if (((card.getTier() == LolTier.D && (card.getTierLev().equals(2)) || (card.getTierLev().equals(1))) || card.getTier() == LolTier.M || card.getTier() == LolTier.GM)
                            && (card.getOwner().getStatus() == UserStatus.INACTIVE || card.getOwner().getStatus() == UserStatus.ACTIVE)) {
                        addCardToProcessedDeck(cards, card);
                        cardCount++;
                    }
                    if (cardCount == MAX_CARD_NUM) break;
                }
                break;
            case GM:
                // D1, M, GM, C
                for (LolDuoProfileCard card : allCards) {
                    if (((card.getTier() == LolTier.D && card.getTierLev().equals(1)) || card.getTier() == LolTier.M || card.getTier() == LolTier.GM || card.getTier() == LolTier.C)
                            && (card.getOwner().getStatus() == UserStatus.INACTIVE || card.getOwner().getStatus() == UserStatus.ACTIVE)) {
                        addCardToProcessedDeck(cards, card);
                        cardCount++;
                    }
                    if (cardCount == MAX_CARD_NUM) break;
                }
                break;
            case C:
                // GM, C
                for (LolDuoProfileCard card : allCards) {
                    if ((card.getTier() == LolTier.GM || card.getTier() == LolTier.C)
                            && (card.getOwner().getStatus() == UserStatus.INACTIVE || card.getOwner().getStatus() == UserStatus.ACTIVE)) {
                        addCardToProcessedDeck(cards, card);
                        cardCount++;
                    }
                    if (cardCount == MAX_CARD_NUM) break;
                }
                break;
            default:
                break;
        }

        deck.setCardCount(cardCount);
        deck.setDuoProfileCards(cards);
        deck.setMakeTime(LocalDateTime.now());
        return deck;
    }
*/

    public LolProcessedProfileCardDeck takeDeck(LolMatchDeckRequestDto requestDto) {
        checkClientVersion(requestDto.getClientVersion());

        LolProcessedProfileCardDeck deck = new LolProcessedProfileCardDeck();
        User owner = userRepository.find(requestDto.getGeneratedId());

        if (owner.getStatus() != UserStatus.ACTIVE) {
            throw new BadMatchRequestException();
        }

        List<LolProfileCard> cardList = lolProfileCardRepository.findCustomizedListV1(owner);
        List<LolProcessedProfileCard> cardDeck = new ArrayList<>();

        for(LolProfileCard card : cardList) {
            User user = card.getOwner();
            UserType userType = null;
            if (userTypeRepository.exist(user)) userType = userTypeRepository.find(user);

            addCardToProcessedDeck(cardDeck, card, userType);
        }

        deck.setCardCount(cardDeck.size());
        deck.setDuoProfileCards(cardDeck);
        deck.setMakeTime(LocalDateTime.now());
        return deck;
    }

    public LolProfileCardResponseDto updateFavorites(LolProfileCardMakeRequestDto requestDto) throws UpdateFailureException {
        checkClientVersion(requestDto.getClientVersion());

        LolProfileCardResponseDto responseDto = new LolProfileCardResponseDto();
        User owner = userRepository.find(requestDto.getGeneratedId());
        LolProfileCard profileCard = lolProfileCardRepository.find(owner);

        lolProfileCardRepository.updateFavorite(profileCard,
                requestDto.getFavoriteTier(),
                requestDto.getFavoriteTop(),
                requestDto.getFavoriteJungle(),
                requestDto.getFavoriteMid(),
                requestDto.getFavoriteAd(),
                requestDto.getFavoriteSupport());

        responseDto.setMatchType(profileCard.getMatchType());
        responseDto.setGameType(profileCard.getGameType());
        responseDto.setChampion1(profileCard.getChampion1());
        responseDto.setChampion2(profileCard.getChampion2());
        responseDto.setChampion3(profileCard.getChampion3());
        responseDto.setTop(profileCard.getTop());
        responseDto.setJungle(profileCard.getJungle());
        responseDto.setMid(profileCard.getMid());
        responseDto.setAd(profileCard.getAd());
        responseDto.setSupport(profileCard.getSupport());
        responseDto.setSummonerName(profileCard.getSummonerName());
        responseDto.setTier(profileCard.getTier());
        responseDto.setTierLev(profileCard.getTierLev());
        responseDto.setLp(profileCard.getLp());
        responseDto.setVoice(profileCard.getVoice());
        responseDto.setMainLolPosition(profileCard.getMainLolPosition());
        responseDto.setFavoriteTier(profileCard.getFavoriteTier());
        responseDto.setFavoriteTop(profileCard.getFavoriteTop());
        responseDto.setFavoriteJungle(profileCard.getFavoriteJungle());
        responseDto.setFavoriteMid(profileCard.getFavoriteMid());
        responseDto.setFavoriteAd(profileCard.getFavoriteAd());
        responseDto.setFavoriteSupport(profileCard.getFavoriteSupport());

        return responseDto;
    }

    public LolProcessedProfileCard getReferral(LolProfileCardRequestDto requestDto) {
        checkClientVersion(requestDto.getClientVersion());

        LolProcessedProfileCard processedProfileCard = new LolProcessedProfileCard();
        ReferralConditions conditions = new ReferralConditions();

        User apiCaller = userRepository.find(requestDto.getGeneratedId());
        LolProfileCard profileCard = lolProfileCardRepository.find(apiCaller);
        conditions.setApiCaller(apiCaller);
        conditions.setTier(profileCard.getTier());

        // 추가적인 추천조건 구현 영역

        List<LolProfileCard> findCards = lolProfileCardRepository.findReferralCard(conditions);
        List<LolRecommendationRefresh> refreshes = lolRecommendationRefreshRepository.find(apiCaller);

        LolProfileCard findCard = checkRecommendation(findCards, refreshes);

        return fillLolProcessedCard(processedProfileCard, apiCaller, findCard, PROCESSED);
    }

    public LolProcessedProfileCardDeck getMissedDeck(LolProfileCardRequestDto requestDto) {
        checkClientVersion(requestDto.getClientVersion());

        LolProcessedProfileCardDeck responseDto = new LolProcessedProfileCardDeck();
        User apiCaller = userRepository.find(requestDto.getGeneratedId());
        List<LolRequest> missedRequests = lolRequestRepository.findMissedRequest(apiCaller);
        List<LolProcessedProfileCard> processedProfileCards = new ArrayList<>();


        for (LolRequest request : missedRequests) {
            // 나에게 매치 신청을 보냈던 유저들의 프로필카드
            User sender = request.getSender();
            UserType userType = null;
            if (userTypeRepository.exist(sender)) userType = userTypeRepository.find(sender);

            LolProfileCard missedProfile = lolProfileCardRepository.findById(sender.getId());
            addCardToProcessedDeck(processedProfileCards, missedProfile, userType);
        }

        responseDto.setDuoProfileCards(processedProfileCards);
        responseDto.setCardCount(processedProfileCards.size());
        responseDto.setMakeTime(LocalDateTime.now());

        return responseDto;
    }


    @NotNull
    private LolProcessedProfileCard fillLolProcessedCard(LolProcessedProfileCard lolProcessedProfileCard, User apiCaller, LolProfileCard findCard, Integer flag) {
        UserType userType = null;
        if (userTypeRepository.exist(apiCaller)) userType = userTypeRepository.find(apiCaller);

        lolProcessedProfileCard.setProfileCardId(findCard.getId());
        lolProcessedProfileCard.setVoice(findCard.getVoice());
        if (flag.equals(ORIGINAL)) lolProcessedProfileCard.setLimitedSummonerName(findCard.getSummonerName());
        else if (flag.equals(PROCESSED)) lolProcessedProfileCard.setLimitedSummonerName(findCard.getSummonerName().charAt(0) + "***");
        lolProcessedProfileCard.setTier(findCard.getTier());
        lolProcessedProfileCard.setLastActiveTime(findCard.getOwner().getLastActiveTime());
        lolProcessedProfileCard.setTierLev(findCard.getTierLev());
        lolProcessedProfileCard.setLp(findCard.getLp());
        lolProcessedProfileCard.setChampion1(findCard.getChampion1());
        lolProcessedProfileCard.setChampion2(findCard.getChampion2());
        lolProcessedProfileCard.setChampion3(findCard.getChampion3());
        lolProcessedProfileCard.setTop(findCard.getTop());
        lolProcessedProfileCard.setJungle(findCard.getJungle());
        lolProcessedProfileCard.setMid(findCard.getMid());
        lolProcessedProfileCard.setAd(findCard.getAd());
        lolProcessedProfileCard.setSupport(findCard.getSupport());
        lolProcessedProfileCard.setMainLolPosition(findCard.getMainLolPosition());
        lolProcessedProfileCard.setMatchType(findCard.getMatchType());
        lolProcessedProfileCard.setFavoriteTier(findCard.getFavoriteTier());
        lolProcessedProfileCard.setFavoriteTop(findCard.getFavoriteTop());
        lolProcessedProfileCard.setFavoriteJungle(findCard.getFavoriteJungle());
        lolProcessedProfileCard.setFavoriteMid(findCard.getFavoriteMid());
        lolProcessedProfileCard.setFavoriteAd(findCard.getFavoriteAd());
        lolProcessedProfileCard.setFavoriteSupport(findCard.getFavoriteSupport());
        lolProcessedProfileCard.setGameType(findCard.getGameType());

        if (userType == null) {
            lolProcessedProfileCard.setUserType(null);
            lolProcessedProfileCard.setEgoTestVersion(null);
        }
        else {
            lolProcessedProfileCard.setUserType(userType.getType());
            lolProcessedProfileCard.setEgoTestVersion(userType.getVersion());
        }

        return lolProcessedProfileCard;
    }


}
