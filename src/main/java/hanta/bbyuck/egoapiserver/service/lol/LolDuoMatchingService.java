package hanta.bbyuck.egoapiserver.service.lol;

import hanta.bbyuck.egoapiserver.domain.Experience;
import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.domain.enumset.ExpStatus;
import hanta.bbyuck.egoapiserver.domain.enumset.Game;
import hanta.bbyuck.egoapiserver.domain.enumset.GameType;
import hanta.bbyuck.egoapiserver.domain.enumset.UserStatus;
import hanta.bbyuck.egoapiserver.domain.lol.LolDuoMatching;
import hanta.bbyuck.egoapiserver.domain.lol.enumset.LolDuoMatchingStatus;
import hanta.bbyuck.egoapiserver.domain.lol.LolDuoProfileCard;
import hanta.bbyuck.egoapiserver.domain.lol.LolDuoRequest;
import hanta.bbyuck.egoapiserver.domain.lol.enumset.LolRequestStatus;
import hanta.bbyuck.egoapiserver.exception.UserAuthenticationException;
import hanta.bbyuck.egoapiserver.exception.UserAuthorizationException;
import hanta.bbyuck.egoapiserver.exception.http.BadRequestException;
import hanta.bbyuck.egoapiserver.repository.ExperienceRepository;
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
    private final ExperienceRepository experienceRepository;

    public void match(LolDuoMatchingRequestDto requestDto) {
        checkClientVersion(requestDto.getClientVersion());

        User reqUser = userRepository.find(requestDto.getGeneratedId());
        User opponent = lolDuoProfileCardRepository.findById(requestDto.getOpponentProfileCardId()).getOwner();

        // 요청 보낸 사람과 요청 받은 사람 잘 체크할 것
        if(lolDuoMatchingRepository.isExist(opponent, reqUser)) {
            throw new BadRequestException("이미 존재하는 매칭");
        }

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

        User requester = matching.getRequester();
        User respondent = matching.getRespondent();

        switch(matching.getMatchingStatus()) {
            case MATCHING_ON:
                if(isBeforeCancelTime(matching.getStartTime(), LocalDateTime.now())) {
                    throw new BadRequestException("1분전 취소 불가능");
                }

                userRepository.updateUserStatus(requester, UserStatus.ACTIVE);
                userRepository.updateUserStatus(respondent, UserStatus.ACTIVE);
                // 매칭 삭제 -> cancel
                lolDuoMatchingRepository.setFinishTime(matching);
                lolDuoMatchingRepository.setMatchingStatus(matching, LolDuoMatchingStatus.CANCEL);

                break;
            case MATCHING:
                // 먼저 api 호출한 유저 매칭 상태-> LOL_DUO_MATCHING_FINISH로 보내서 평가창 무조건 뜨도록 고정
                // 아직 방을 안나온 유저는 MATCHING 상태

                userRepository.updateUserStatus(apiCaller, UserStatus.LOL_DUO_MATCHING_FINISH);
                lolDuoMatchingRepository.setOffTime(matching);
                lolDuoMatchingRepository.setMatchingStatus(matching, LolDuoMatchingStatus.MATCHING_OFF);

                if (!isBeforeTenMinutes(matching.getStartTime(), LocalDateTime.now())) {
                    // 10분 이후라면 경험치 제공 -> 경험치 테이블에 저장
                    Experience experience = new Experience();
                    experience.makeExp(apiCaller, Game.LOL, GameType.DUO, ExpStatus.COMPLETE);
                    experienceRepository.save(experience);
                }

                break;
            case MATCHING_OFF:
                // 나중에 나오는 유저도 10분 이전에 나오면 경험치 안줌
                userRepository.updateUserStatus(apiCaller, UserStatus.LOL_DUO_MATCHING_FINISH);
                lolDuoMatchingRepository.setMatchingStatus(matching, LolDuoMatchingStatus.FINISHED);
                lolDuoMatchingRepository.setFinishTime(matching);

                if (!isBeforeTenMinutes(matching.getStartTime(), LocalDateTime.now())) {
                    // 10분 이후라면 경험치 제공 -> 경험치 테이블에 저장

                    Experience experience = new Experience();
                    experience.makeExp(apiCaller, Game.LOL, GameType.DUO, ExpStatus.COMPLETE);
                    experienceRepository.save(experience);
                }
                break;
            default:
                throw new BadRequestException("존재하지 않는 매칭입니다.");
        }
    }

    public LolDuoMatchingResponseDto findMatch(LolDuoMatchingRequestDto requestDto) {
        checkClientVersion(requestDto.getClientVersion());

        try {
            User apiCaller = userRepository.find(requestDto.getGeneratedId());

            if (!apiCaller.getStatus().equals(UserStatus.LOL_DUO_MATCHING) && !apiCaller.getStatus().equals(UserStatus.LOL_DUO_MATCHING_READY) && !apiCaller.getStatus().equals(UserStatus.LOL_DUO_MATCHING_FINISH)) {
                throw new BadRequestException("현재 롤 듀오 매칭중이 아닙니다.");
            }

            LolDuoMatching matching = lolDuoMatchingRepository.find(apiCaller);

            LolDuoProfileCard apiCallerCard = lolDuoProfileCardRepository.find(apiCaller);
            LolDuoProfileCard opponentCard = null;

            if (matching.getRespondent() == apiCaller) {
                opponentCard = lolDuoProfileCardRepository.find(matching.getRequester());
            } else if (matching.getRequester() == apiCaller) {
                opponentCard = lolDuoProfileCardRepository.find(matching.getRespondent());
            }

            return makeResponse(apiCallerCard, opponentCard, matching.getId());

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

    public void enterMatch(LolDuoMatchingRequestDto requestDto) {
        checkClientVersion(requestDto.getClientVersion());


        User apiCaller = userRepository.find(requestDto.getGeneratedId());
        LolDuoMatching matching = lolDuoMatchingRepository.find(apiCaller);

        if (matching.getMatchingStatus().equals(LolDuoMatchingStatus.MATCHING)){
            throw new BadRequestException("이미 방에 입장");
        }

        LolDuoRequest request = lolDuoRequestRepository.findRequest(apiCaller, matching.getRespondent());
        lolDuoRequestRepository.updateRequestStatus(request, LolRequestStatus.FINISHED);

        lolDuoRequestRepository.updateAllSentRequest(matching.getRequester(), LolRequestStatus.CANCELED);
        lolDuoRequestRepository.updateAllSentRequest(matching.getRespondent(), LolRequestStatus.CANCELED);

        userRepository.updateUserStatus(apiCaller, UserStatus.LOL_DUO_MATCHING);
        lolDuoMatchingRepository.setMatchingStatus(matching, LolDuoMatchingStatus.MATCHING);

        // fcm 관련 로직
    }

    public void evaluateOpponent(LolDuoMatchingRequestDto requestDto) {

    }

    public void reportOpponent(LolDuoMatchingRequestDto requestDto) {
    }
}
