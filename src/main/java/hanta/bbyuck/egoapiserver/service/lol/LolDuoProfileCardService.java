package hanta.bbyuck.egoapiserver.service.lol;

import hanta.bbyuck.egoapiserver.response.lol.LimitedLolDuoProfileCard;
import hanta.bbyuck.egoapiserver.response.lol.LolDuoProfileCardDeck;
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

@Service
@RequiredArgsConstructor
public class LolDuoProfileCardService {
    private final LolDuoProfileCardRepository lolDuoProfileCardRepository;
    private final UserRepository userRepository;

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

    public LolDuoProfileCardDeck takeDeck(LolDuoMatchDeckRequestDto requestDto) {
        LolDuoProfileCardDeck deck = new LolDuoProfileCardDeck();
        User owner = userRepository.find(requestDto.getUserAuth());

        List<LolDuoProfileCard> cardList = lolDuoProfileCardRepository.findCustomizedList(owner);
        List<LimitedLolDuoProfileCard> cards = new ArrayList<>();

        for(LolDuoProfileCard card : cardList) {
            LimitedLolDuoProfileCard processedCard = new LimitedLolDuoProfileCard();
            processedCard.setProfileCardId(card.getId());
            processedCard.setVoice(card.getVoice());
            processedCard.setLimitedSummonerName(card.getSummonerName().substring(0,1) + "***");
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

        deck.setCardCount(cards.size());
        deck.setDuoProfileCards(cards);
        deck.setMakeTime(LocalDateTime.now());
        return deck;
    }
}
