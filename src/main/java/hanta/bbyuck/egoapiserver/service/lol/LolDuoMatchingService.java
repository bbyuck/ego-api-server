package hanta.bbyuck.egoapiserver.service.lol;

import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.domain.enumset.UserStatus;
import hanta.bbyuck.egoapiserver.domain.lol.LolDuoMatching;
import hanta.bbyuck.egoapiserver.domain.lol.enumset.LolDuoMatchingStatus;
import hanta.bbyuck.egoapiserver.domain.lol.LolDuoProfileCard;
import hanta.bbyuck.egoapiserver.domain.lol.LolDuoRequest;
import hanta.bbyuck.egoapiserver.exception.http.BadRequestException;
import hanta.bbyuck.egoapiserver.repository.UserRepository;
import hanta.bbyuck.egoapiserver.repository.lol.LolDuoMatchingRepository;
import hanta.bbyuck.egoapiserver.repository.lol.LolDuoProfileCardRepository;
import hanta.bbyuck.egoapiserver.repository.lol.LolDuoRequestRepository;
import hanta.bbyuck.egoapiserver.request.lol.LolDuoMatchingRequestDto;
import hanta.bbyuck.egoapiserver.response.lol.LolDuoMatchingResponseDto;
import hanta.bbyuck.egoapiserver.util.ClientVersionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LolDuoMatchingService {
    private final LolDuoMatchingRepository lolDuoMatchingRepository;
    private final LolDuoProfileCardRepository lolDuoProfileCardRepository;
    private final LolDuoRequestRepository lolDuoRequestRepository;
    private final UserRepository userRepository;
    private final ClientVersionManager clientVersionManager;

    public void match(LolDuoMatchingRequestDto requestDto) {
        clientVersionManager.checkClientVersion(requestDto.getClientVersion());

        User reqUser = userRepository.find(requestDto.getGeneratedId());
        User opponent = lolDuoProfileCardRepository.findById(requestDto.getOpponentProfileCardId()).getOwner();

        // 요청 보낸 사람과 요청 받은 사람 잘 체크할 것
        LolDuoRequest request = lolDuoRequestRepository.findRequest(opponent, reqUser);
        lolDuoRequestRepository.remove(request);

        LolDuoMatching matching = new LolDuoMatching();
        matching.assignRequester(opponent);
        matching.assignRespondent(reqUser);
        matching.setStartTime();
        matching.setMatchingStatus(LolDuoMatchingStatus.MATCHING_ON);

        /*
         * reqUser는 서버로 요청을 보낸 유저 -> respondent
         * opponent는 카드를 보고 매치 요청을 보낸 유저 -> requester
         */
        reqUser.updateUserStatus(UserStatus.LOL_DUO_MATCHING);
        opponent.updateUserStatus(UserStatus.LOL_DUO_MATCHING_READY);


        lolDuoMatchingRepository.save(matching);
    }

    public void completeMatch(LolDuoMatchingRequestDto requestDto) {
        clientVersionManager.checkClientVersion(requestDto.getClientVersion());

        User reqUser = userRepository.find(requestDto.getGeneratedId());


        try {
            LolDuoMatching matching = lolDuoMatchingRepository.find(reqUser);
            matching.getRequester().updateUserStatus(UserStatus.ACTIVE);
            matching.getRespondent().updateUserStatus(UserStatus.ACTIVE);

            lolDuoMatchingRepository.remove(matching);
        } catch (NoResultException e) {
            throw new BadRequestException("존재하지 않는 매치입니다.");
        }
    }

    public LolDuoMatchingResponseDto findMatch(LolDuoMatchingRequestDto requestDto) {
        try {
            User reqUser = userRepository.find(requestDto.getGeneratedId());
            LolDuoMatching matching = lolDuoMatchingRepository.find(reqUser);

            LolDuoProfileCard reqUserCard = lolDuoProfileCardRepository.find(reqUser);
            LolDuoProfileCard opponentCard = null;

            if (matching.getRespondent() == reqUser) {
                opponentCard = lolDuoProfileCardRepository.find(matching.getRequester());
            } else if (matching.getRequester() == reqUser) {
                opponentCard = lolDuoProfileCardRepository.find(matching.getRespondent());
            }

            return makeResponse(reqUserCard, opponentCard);

        } catch(NoResultException e) {
            throw new BadRequestException("존재하지 않는 매치입니다.");
        }
    }


    private LolDuoMatchingResponseDto makeResponse(LolDuoProfileCard reqCard, LolDuoProfileCard opponentCard) {
        LolDuoMatchingResponseDto responseDto = new LolDuoMatchingResponseDto();


        responseDto.setMyVoice(reqCard.getVoice());
        responseDto.setMySummonerName(reqCard.getSummonerName());
        responseDto.setMyTier(reqCard.getTier());
        responseDto.setMyTierLev(reqCard.getTierLev());
        responseDto.setMyLp(reqCard.getLp());
        responseDto.setMyChampion1(reqCard.getChampion1());
        responseDto.setMyChampion2(reqCard.getChampion2());
        responseDto.setMyChampion3(reqCard.getChampion3());
        responseDto.setMyTop(reqCard.getTop());
        responseDto.setMyJungle(reqCard.getJungle());
        responseDto.setMyMid(reqCard.getMid());
        responseDto.setMyAd(reqCard.getAd());
        responseDto.setMySupport(reqCard.getSupport());
        responseDto.setMyMainLolPosition(reqCard.getMainLolPosition());

        responseDto.setOpponentVoice(opponentCard.getVoice());;
        responseDto.setOpponentSummonerName(opponentCard.getSummonerName());
        responseDto.setOpponentTier(opponentCard.getTier());
        responseDto.setOpponentTierLev(opponentCard.getTierLev());
        responseDto.setOpponentLp(opponentCard.getLp());
        responseDto.setOpponentChampion1(opponentCard.getChampion1());
        responseDto.setOpponentChampion2(opponentCard.getChampion2());
        responseDto.setOpponentChampion3(opponentCard.getChampion3());
        responseDto.setOpponentTop(opponentCard.getTop());
        responseDto.setOpponentJungle(opponentCard.getJungle());
        responseDto.setOpponentMid(opponentCard.getMid());
        responseDto.setOpponentAd(opponentCard.getAd());
        responseDto.setOpponentSupport(opponentCard.getSupport());
        responseDto.setOpponentMainLolPosition(opponentCard.getMainLolPosition());

        responseDto.setMatchStartTime(LocalDateTime.now());

        return responseDto;
    }
}
