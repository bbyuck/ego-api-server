package hanta.bbyuck.egoapiserver.service;

import hanta.bbyuck.egoapiserver.domain.DuoProfileCard;
import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.exception.logical.AlreadyOwnProfileCardException;
import hanta.bbyuck.egoapiserver.exception.logical.DuplicateSummonerNameException;
import hanta.bbyuck.egoapiserver.repository.DuoProfileCardRepository;
import hanta.bbyuck.egoapiserver.repository.UserRepository;
import hanta.bbyuck.egoapiserver.request.DuoProfileCardMakeRequest;
import hanta.bbyuck.egoapiserver.request.DuoProfileCardRequest;
import hanta.bbyuck.egoapiserver.response.dto.DuoProfileCardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DuoProfileCardService {
    private final DuoProfileCardRepository duoProfileCardRepository;
    private final UserRepository userRepository;

    public Long makeDuoProfileCard(DuoProfileCardMakeRequest duoProfileCardMakeRequest)  {
        User reqUser = userRepository.find(duoProfileCardMakeRequest.getOwnerAuth());

        if(reqUser.ownDuoProfileCard()) {
            throw new AlreadyOwnProfileCardException("이미 듀오 프로필 카드를 보유하고 있습니다.");
        }

        if (duoProfileCardRepository.isExistSummonerName(duoProfileCardMakeRequest.getSummonerName())){
            throw new DuplicateSummonerNameException("이미 등록된 소환사명입니다.");
        }

        DuoProfileCard duoProfileCard = new DuoProfileCard();
        duoProfileCard.makeProfileCard(reqUser,
                duoProfileCardMakeRequest.getVoice(),
                duoProfileCardMakeRequest.getSummonerName(),
                duoProfileCardMakeRequest.getTier(),
                duoProfileCardMakeRequest.getTierLev(),
                duoProfileCardMakeRequest.getLp(),
                duoProfileCardMakeRequest.getChampion1(),
                duoProfileCardMakeRequest.getChampion2(),
                duoProfileCardMakeRequest.getChampion3(),
                duoProfileCardMakeRequest.getTop(),
                duoProfileCardMakeRequest.getJungle(),
                duoProfileCardMakeRequest.getMid(),
                duoProfileCardMakeRequest.getAd(),
                duoProfileCardMakeRequest.getSupport(),
                duoProfileCardMakeRequest.getMainPosition());
        return duoProfileCardRepository.save(duoProfileCard);
    }

    public DuoProfileCardResponse take(DuoProfileCardRequest request) {
        DuoProfileCard findCard = duoProfileCardRepository.find(request.getDuoProfileCardId());

        // 본인의 카드를 조회한 것이 맞는지 확인
        userRepository.authCheck(findCard.getOwner(), request.getOwnerAuth());

        DuoProfileCardResponse response = new DuoProfileCardResponse();

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
