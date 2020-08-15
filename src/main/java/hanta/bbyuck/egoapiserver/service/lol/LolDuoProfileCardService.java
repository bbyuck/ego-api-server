package hanta.bbyuck.egoapiserver.service.lol;

import hanta.bbyuck.egoapiserver.domain.UserStatus;
import hanta.bbyuck.egoapiserver.exception.http.BadRequestException;
import hanta.bbyuck.egoapiserver.response.lol.LolProcessedDuoProfileCard;
import hanta.bbyuck.egoapiserver.response.lol.LolProcessedDuoProfileCardDeck;
import hanta.bbyuck.egoapiserver.domain.lol.LolDuoProfileCard;
import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.exception.lol.AlreadyOwnProfileCardException;
import hanta.bbyuck.egoapiserver.exception.lol.DuplicateSummonerNameException;
import hanta.bbyuck.egoapiserver.exception.lol.LolDuoProfileCardNotExistException;
import hanta.bbyuck.egoapiserver.repository.lol.LolDuoProfileCardRepository;
import hanta.bbyuck.egoapiserver.repository.UserRepository;
import hanta.bbyuck.egoapiserver.request.lol.LolDuoMatchDeckRequestDto;
import hanta.bbyuck.egoapiserver.request.lol.LolDuoProfileCardMakeRequestDto;
import hanta.bbyuck.egoapiserver.request.lol.LolDuoProfileCardRequestDto;
import hanta.bbyuck.egoapiserver.request.lol.LolDuoProfileCardUpdateRequestDto;
import hanta.bbyuck.egoapiserver.response.lol.LolDuoProfileCardResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static hanta.bbyuck.egoapiserver.util.ServiceUtil.addCardToProcessedDeck;

@Service
@RequiredArgsConstructor
public class LolDuoProfileCardService {
    private final LolDuoProfileCardRepository lolDuoProfileCardRepository;
    private final UserRepository userRepository;
    private static final int MAX_CARD_NUM = 100;

    public void makeDuoProfileCard(LolDuoProfileCardMakeRequestDto lolDuoProfileCardMakeRequestDto) throws NoResultException{
        User reqUser = userRepository.find(lolDuoProfileCardMakeRequestDto.getOwnerAuth());

        if(lolDuoProfileCardRepository.isExist(reqUser)) {
            throw new AlreadyOwnProfileCardException("이미 듀오 프로필 카드를 보유하고 있습니다.");
        }

        if (lolDuoProfileCardRepository.isExistSummonerName(lolDuoProfileCardMakeRequestDto.getSummonerName())){
            throw new DuplicateSummonerNameException("이미 등록된 소환사명입니다.");
        }

        LolDuoProfileCard lolDuoProfileCard = new LolDuoProfileCard();
        lolDuoProfileCard.makeProfileCard(
                reqUser,
                lolDuoProfileCardMakeRequestDto.getVoice(),
                lolDuoProfileCardMakeRequestDto.getSummonerName(),
                lolDuoProfileCardMakeRequestDto.getTier(),
                lolDuoProfileCardMakeRequestDto.getTierLev(),
                lolDuoProfileCardMakeRequestDto.getLp(),
                lolDuoProfileCardMakeRequestDto.getChampion1(),
                lolDuoProfileCardMakeRequestDto.getChampion2(),
                lolDuoProfileCardMakeRequestDto.getChampion3(),
                lolDuoProfileCardMakeRequestDto.getTop(),
                lolDuoProfileCardMakeRequestDto.getJungle(),
                lolDuoProfileCardMakeRequestDto.getMid(),
                lolDuoProfileCardMakeRequestDto.getAd(),
                lolDuoProfileCardMakeRequestDto.getSupport(),
                lolDuoProfileCardMakeRequestDto.getMainLolPosition());
        lolDuoProfileCardRepository.save(lolDuoProfileCard);
    }

    public LolDuoProfileCardResponseDto take(LolDuoProfileCardRequestDto request) {
        try {
            User reqUser = userRepository.find(request.getOwnerAuth());
            LolDuoProfileCard findCard = lolDuoProfileCardRepository.find(reqUser);

            LolDuoProfileCardResponseDto response = new LolDuoProfileCardResponseDto();

            response.setVoice(findCard.getVoice());
            response.setSummonerName(findCard.getSummonerName());
            response.setTier(findCard.getTier());
            response.setTierLev(findCard.getTierLev());
            response.setLp(findCard.getLp());
            response.setChampion1(findCard.getChampion1());
            response.setChampion2(findCard.getChampion2());
            response.setChampion3(findCard.getChampion3());
            response.setTop(findCard.getTop());
            response.setJungle(findCard.getJungle());
            response.setMid(findCard.getMid());
            response.setAd(findCard.getAd());
            response.setSupport(findCard.getSupport());
            response.setMainLolPosition(findCard.getMainLolPosition());

            return response;
        } catch (NoResultException e) {
            throw new LolDuoProfileCardNotExistException("프로필 카드가 존재하지 않습니다.");
        }
    }

    public void updateMyCard(LolDuoProfileCardUpdateRequestDto requestDto) {
        // 수정시 authCheck 할 것
        userRepository.authCheck(requestDto.getOwnerAuth());

        User owner = userRepository.find(requestDto.getOwnerAuth());
        LolDuoProfileCard lolDuoProfileCard = lolDuoProfileCardRepository.find(owner);

        lolDuoProfileCardRepository.update(lolDuoProfileCard, requestDto);
    }

    /*
     * takeDeck V2 -> 전체 뽑아오기 별로 성능 안좋음
     */
/*
    public LolDuoProfileCardDeck takeDeck(LolDuoMatchDeckRequestDto requestDto) {
        LolDuoProfileCardDeck deck = new LolDuoProfileCardDeck();
        User owner = userRepository.find(requestDto.getUserAuth());

        LolDuoProfileCard reqUserCard = lolDuoProfileCardRepository.find(owner);
        List<LolDuoProfileCard> allCards = lolDuoProfileCardRepository.findAllOrderByActiveTime();

        List<ProcessedLolDuoProfileCard> cards = new ArrayList<>();
        int cardCount = 0;

        switch (reqUserCard.getTier()) {
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
                if(reqUserCard.getTierLev() == 1) {
                    for (LolDuoProfileCard card : allCards) {
                        if ((card.getTier() == LolTier.G || card.getTier() == LolTier.P || ((card.getTier() == LolTier.D) && ((card.getTierLev().equals(3)) || card.getTierLev().equals(4))))
                                && (card.getOwner().getStatus() == UserStatus.INACTIVE || card.getOwner().getStatus() == UserStatus.ACTIVE)) {
                            addCardToProcessedDeck(cards, card);
                            cardCount++;
                        }
                        if (cardCount == MAX_CARD_NUM) break;
                    }
                }
                else if(reqUserCard.getTierLev() == 2) {
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
                if(reqUserCard.getTierLev() == 4) {
                    for (LolDuoProfileCard card : allCards) {
                        if (((card.getTier() == LolTier.P && (card.getTierLev().equals(2) || card.getTierLev().equals(1))) || (card.getTier() == LolTier.D && !card.getTierLev().equals(1)))
                                && (card.getOwner().getStatus() == UserStatus.INACTIVE || card.getOwner().getStatus() == UserStatus.ACTIVE)) {
                            addCardToProcessedDeck(cards, card);
                            cardCount++;
                        }
                        if (cardCount == MAX_CARD_NUM) break;
                    }
                }
                else if (reqUserCard.getTierLev() == 3) {
                    for (LolDuoProfileCard card : allCards) {
                        if (((card.getTier() == LolTier.P && card.getTierLev().equals(1)) || card.getTier() == LolTier.D)
                                && (card.getOwner().getStatus() == UserStatus.INACTIVE || card.getOwner().getStatus() == UserStatus.ACTIVE)) {
                            addCardToProcessedDeck(cards, card);
                            cardCount++;
                        }
                        if (cardCount == MAX_CARD_NUM) break;
                    }
                }
                else if (reqUserCard.getTierLev() == 2) {
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

    public LolProcessedDuoProfileCardDeck takeDeck(LolDuoMatchDeckRequestDto requestDto) {
        LolProcessedDuoProfileCardDeck deck = new LolProcessedDuoProfileCardDeck();
        User owner = userRepository.find(requestDto.getUserAuth());

        if (owner.getStatus() != UserStatus.INACTIVE && owner.getStatus() != UserStatus.ACTIVE) {
            throw new BadRequestException("이미 매칭에 참여중인 유저입니다.");
        }

        List<LolDuoProfileCard> cardList = lolDuoProfileCardRepository.findCustomizedListV1(owner);
        List<LolProcessedDuoProfileCard> cardDeck = new ArrayList<>();

        for(LolDuoProfileCard card : cardList) {
            addCardToProcessedDeck(cardDeck, card);
        }

        deck.setCardCount(cardDeck.size());
        deck.setDuoProfileCards(cardDeck);
        deck.setMakeTime(LocalDateTime.now());
        return deck;
    }
}
