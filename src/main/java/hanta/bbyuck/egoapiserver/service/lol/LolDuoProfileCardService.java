package hanta.bbyuck.egoapiserver.service.lol;

import hanta.bbyuck.egoapiserver.domain.lol.LolDuoProfileCard;
import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.exception.lol.AlreadyOwnProfileCardException;
import hanta.bbyuck.egoapiserver.exception.lol.DuplicateSummonerNameException;
import hanta.bbyuck.egoapiserver.exception.lol.ProfileCardNotExistException;
import hanta.bbyuck.egoapiserver.repository.lol.LolDuoProfileCardRepository;
import hanta.bbyuck.egoapiserver.repository.UserRepository;
import hanta.bbyuck.egoapiserver.request.lol.LolDuoProfileCardMakeRequestDto;
import hanta.bbyuck.egoapiserver.request.lol.LolDuoProfileCardRequestDto;
import hanta.bbyuck.egoapiserver.response.lol.LolDuoProfileCardResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LolDuoProfileCardService {
    private final LolDuoProfileCardRepository lolDuoProfileCardRepository;
    private final UserRepository userRepository;

    public Long makeDuoProfileCard(LolDuoProfileCardMakeRequestDto lolDuoProfileCardMakeRequestDto)  {
        User reqUser = userRepository.find(lolDuoProfileCardMakeRequestDto.getOwnerAuth());

        if(reqUser.ownLolDuoProfileCard()) {
            throw new AlreadyOwnProfileCardException("이미 듀오 프로필 카드를 보유하고 있습니다.");
        }

        if (lolDuoProfileCardRepository.isExistSummonerName(lolDuoProfileCardMakeRequestDto.getSummonerName())){
            throw new DuplicateSummonerNameException("이미 등록된 소환사명입니다.");
        }

        LolDuoProfileCard lolDuoProfileCard = new LolDuoProfileCard();
        lolDuoProfileCard.makeProfileCard(reqUser,
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
                lolDuoProfileCardMakeRequestDto.getMainPosition());
        return lolDuoProfileCardRepository.save(lolDuoProfileCard);
    }

    public LolDuoProfileCardResponseDto take(LolDuoProfileCardRequestDto request) {
        User reqUser = userRepository.find(request.getOwnerAuth());

        if(!reqUser.ownLolDuoProfileCard()) {
            throw new ProfileCardNotExistException("프로필 카드가 존재하지 않습니다.");
        }

        LolDuoProfileCard findCard = reqUser.getLolDuoProfileCard();
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
        response.setMainPosition(findCard.getMainPosition());

        return response;
    }
}
