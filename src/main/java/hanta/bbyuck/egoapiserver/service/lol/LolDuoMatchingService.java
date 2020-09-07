package hanta.bbyuck.egoapiserver.service.lol;

import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.domain.enumset.UserStatus;
import hanta.bbyuck.egoapiserver.domain.lol.LolDuoMatching;
import hanta.bbyuck.egoapiserver.domain.lol.enumset.LolDuoMatchingStatus;
import hanta.bbyuck.egoapiserver.domain.lol.LolDuoProfileCard;
import hanta.bbyuck.egoapiserver.domain.lol.LolDuoRequest;
import hanta.bbyuck.egoapiserver.exception.UserAuthenticationException;
import hanta.bbyuck.egoapiserver.exception.UserAuthorizationException;
import hanta.bbyuck.egoapiserver.exception.http.BadRequestException;
import hanta.bbyuck.egoapiserver.repository.UserRepository;
import hanta.bbyuck.egoapiserver.repository.lol.LolDuoMatchingRepository;
import hanta.bbyuck.egoapiserver.repository.lol.LolDuoProfileCardRepository;
import hanta.bbyuck.egoapiserver.repository.lol.LolDuoRequestRepository;
import hanta.bbyuck.egoapiserver.request.lol.LolDuoMatchingRequestDto;
import hanta.bbyuck.egoapiserver.response.lol.LolDuoMatchingResponseDto;
import hanta.bbyuck.egoapiserver.util.ClientVersionManager;
import hanta.bbyuck.egoapiserver.util.TimeCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.time.LocalDateTime;

import static hanta.bbyuck.egoapiserver.util.ClientVersionManager.*;
import static hanta.bbyuck.egoapiserver.util.TimeCalculator.*;

@Service
@RequiredArgsConstructor
public class LolDuoMatchingService {
    private final LolDuoMatchingRepository lolDuoMatchingRepository;
    private final LolDuoProfileCardRepository lolDuoProfileCardRepository;
    private final LolDuoRequestRepository lolDuoRequestRepository;
    private final UserRepository userRepository;

    public void match(LolDuoMatchingRequestDto requestDto) {
        checkClientVersion(requestDto.getClientVersion());

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
        checkClientVersion(requestDto.getClientVersion());

        LolDuoMatching matching = lolDuoMatchingRepository.findById(requestDto.getMatchId());
        User apiCaller = userRepository.find(requestDto.getGeneratedId());

        // api 호출자가 다른 사람의 매칭에 대해 요청한 경우 권한 없음 예외 처리
        if (matching.getRequester() != apiCaller && matching.getRespondent() != apiCaller) {
            throw new UserAuthorizationException();
        }
/*
 *                  1. 매칭상태 : MATCHING_ON -> 요청 수락한 유저(respondent)가 임의로 종료 -> 요청 보낸 유저(requester)상태 ACTIVE로 변경
                    2. 매칭상태 : MATCHING -> 매칭에 참여한 유저 둘 중 아무나 종료 -> 유저 상태 둘다 변경필요
                    3. 매칭상태 : MATCHING_OFF -> 매칭상태만 MATCHING_FINISH로 저장하고 평가 테이블로")
 */

        User requester = matching.getRequester();
        User respondent = matching.getRespondent();

        switch(matching.getMatchingStatus()) {
            case MATCHING_ON:
                userRepository.updateUserStatus(requester, UserStatus.ACTIVE);
                userRepository.updateUserStatus(respondent, UserStatus.ACTIVE);
                matching.setFinishTime();
                matching.setMatchingStatus(LolDuoMatchingStatus.CANCEL);
                break;
            case MATCHING:
                userRepository.updateUserStatus(apiCaller, UserStatus.LOL_DUO_MATCHING_FINISH);
                if (isBeforeTenMinutes(matching.getStartTime(), LocalDateTime.now())) {

                }
                else {

                }

                break;
            case MATCHING_OFF:
                break;
            default:
                break;
        }


        try {
            lolDuoMatchingRepository.remove(matching);
        } catch (NoResultException e) {
            throw new BadRequestException("존재하지 않는 매치입니다.");
        }
    }

    public LolDuoMatchingResponseDto findMatch(LolDuoMatchingRequestDto requestDto) {
        checkClientVersion(requestDto.getClientVersion());

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

            return makeResponse(reqUserCard, opponentCard, matching.getId());

        } catch(NoResultException e) {
            throw new BadRequestException("존재하지 않는 매치입니다.");
        }
    }


    private LolDuoMatchingResponseDto makeResponse(LolDuoProfileCard reqCard, LolDuoProfileCard opponentCard, Long matchId) {
        LolDuoMatchingResponseDto responseDto = new LolDuoMatchingResponseDto();

        responseDto.setMatchId(matchId);

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
