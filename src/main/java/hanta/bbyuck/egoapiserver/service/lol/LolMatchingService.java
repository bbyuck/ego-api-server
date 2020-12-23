package hanta.bbyuck.egoapiserver.service.lol;

import hanta.bbyuck.egoapiserver.domain.Experience;
import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.domain.enumset.*;
import hanta.bbyuck.egoapiserver.domain.lol.LolMatching;
import hanta.bbyuck.egoapiserver.domain.lol.enumset.LolMatchingStatus;
import hanta.bbyuck.egoapiserver.domain.lol.LolProfileCard;
import hanta.bbyuck.egoapiserver.domain.lol.LolRequest;
import hanta.bbyuck.egoapiserver.domain.lol.enumset.LolRequestStatus;
import hanta.bbyuck.egoapiserver.exception.MatchNotExistException;
import hanta.bbyuck.egoapiserver.exception.UserAuthorizationException;
import hanta.bbyuck.egoapiserver.exception.http.BadRequestException;
import hanta.bbyuck.egoapiserver.repository.ExperienceRepository;
import hanta.bbyuck.egoapiserver.repository.UserRepository;
import hanta.bbyuck.egoapiserver.repository.lol.LolMatchingRepository;
import hanta.bbyuck.egoapiserver.repository.lol.LolProfileCardRepository;
import hanta.bbyuck.egoapiserver.repository.lol.LolRequestRepository;
import hanta.bbyuck.egoapiserver.request.lol.LolMatchingRequestDto;
import hanta.bbyuck.egoapiserver.response.lol.LolMatchingResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.time.LocalDateTime;

import static hanta.bbyuck.egoapiserver.util.ClientVersionManager.*;
import static hanta.bbyuck.egoapiserver.util.TimeCalculator.*;

@Service
@RequiredArgsConstructor
public class LolMatchingService {
    private final LolMatchingRepository lolMatchingRepository;
    private final LolProfileCardRepository lolProfileCardRepository;
    private final LolRequestRepository lolRequestRepository;
    private final UserRepository userRepository;
    private final ExperienceRepository experienceRepository;

    public void match(LolMatchingRequestDto requestDto) {
        checkClientVersion(requestDto.getClientVersion());

        User reqUser = userRepository.find(requestDto.getGeneratedId());
        User opponent = lolProfileCardRepository.findById(requestDto.getOpponentProfileId()).getOwner();

        // 요청 보낸 사람과 요청 받은 사람 잘 체크할 것
        if(lolMatchingRepository.isExist(opponent, reqUser)) {
            throw new BadRequestException("이미 존재하는 매칭");
        }

        if(!lolRequestRepository.isExistRequest(opponent, reqUser)) {
            throw new BadRequestException("잘못된 매칭 요청");
        }

        LolMatching matching = new LolMatching();
        matching.assignRequester(opponent);
        matching.assignRespondent(reqUser);
        matching.setStartTime();
        matching.setMatchingStatus(LolMatchingStatus.MATCHING_ON);
        matching.setMatchType(requestDto.getMatchType());
        /*
         * reqUser는 서버로 요청을 보낸 유저 -> respondent
         * opponent는 카드를 보고 매치 요청을 보낸 유저 -> requester
         */
        reqUser.updateUserStatus(UserStatus.LOL_DUO_MATCHING);
        opponent.updateUserStatus(UserStatus.LOL_DUO_MATCHING_READY);


        lolMatchingRepository.save(matching);
    }

    public void completeMatch(LolMatchingRequestDto requestDto) {
        checkClientVersion(requestDto.getClientVersion());

        LolMatching matching = lolMatchingRepository.findById(requestDto.getMatchId());
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
                lolMatchingRepository.setDuoFinishTime(matching);
                lolMatchingRepository.setDuoMatchingStatus(matching, LolMatchingStatus.CANCEL);

                break;
            case MATCHING:
                // 먼저 api 호출한 유저 매칭 상태-> LOL_DUO_MATCHING_FINISH로 보내서 평가창 무조건 뜨도록 고정
                // 아직 방을 안나온 유저는 MATCHING 상태

                userRepository.updateUserStatus(apiCaller, UserStatus.LOL_DUO_MATCHING_FINISH);
                lolMatchingRepository.setDuoOffTime(matching);
                lolMatchingRepository.setDuoMatchingStatus(matching, LolMatchingStatus.MATCHING_OFF);

                if (!isBeforeTenMinutes(matching.getStartTime(), LocalDateTime.now())) {
                    // 10분 이후라면 경험치 제공 -> 경험치 테이블에 저장
                    Experience experience = new Experience();
                    experience.makeExp(apiCaller, Game.LOL, MatchType.DUO, ExpStatus.COMPLETE);
                    experienceRepository.save(experience);
                }

                break;
            case MATCHING_OFF:
                // 나중에 나오는 유저도 10분 이전에 나오면 경험치 안줌
                userRepository.updateUserStatus(apiCaller, UserStatus.LOL_DUO_MATCHING_FINISH);
                lolMatchingRepository.setDuoMatchingStatus(matching, LolMatchingStatus.FINISHED);
                lolMatchingRepository.setDuoFinishTime(matching);

                if (!isBeforeTenMinutes(matching.getStartTime(), LocalDateTime.now())) {
                    // 10분 이후라면 경험치 제공 -> 경험치 테이블에 저장

                    Experience experience = new Experience();
                    experience.makeExp(apiCaller, Game.LOL, MatchType.DUO, ExpStatus.COMPLETE);
                    experienceRepository.save(experience);
                }
                break;
            default:
                throw new BadRequestException("존재하지 않는 매칭입니다.");
        }
    }

    public LolMatchingResponseDto findMatch(LolMatchingRequestDto requestDto) {
        checkClientVersion(requestDto.getClientVersion());

        try {
            User apiCaller = userRepository.find(requestDto.getGeneratedId());

            if (!apiCaller.getStatus().equals(UserStatus.LOL_DUO_MATCHING) && !apiCaller.getStatus().equals(UserStatus.LOL_DUO_MATCHING_READY) && !apiCaller.getStatus().equals(UserStatus.LOL_DUO_MATCHING_FINISH)) {
                throw new MatchNotExistException();
            }

            LolMatching matching = lolMatchingRepository.findDuoMatching(apiCaller);

            LolProfileCard apiCallerCard = lolProfileCardRepository.find(apiCaller);
            LolProfileCard opponentCard = null;

            if (matching.getRespondent() == apiCaller) {
                opponentCard = lolProfileCardRepository.find(matching.getRequester());
            } else if (matching.getRequester() == apiCaller) {
                opponentCard = lolProfileCardRepository.find(matching.getRespondent());
            }

            return makeResponse(apiCallerCard, opponentCard, matching.getId());

        } catch(NoResultException e) {
            throw new BadRequestException("존재하지 않는 매치입니다.");
        }
    }


    private LolMatchingResponseDto makeResponse(LolProfileCard reqCard, LolProfileCard opponentCard, Long matchId) {
        LolMatchingResponseDto responseDto = new LolMatchingResponseDto();

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

    public void enterMatch(LolMatchingRequestDto requestDto) {
        checkClientVersion(requestDto.getClientVersion());


        User apiCaller = userRepository.find(requestDto.getGeneratedId());
        LolMatching matching = lolMatchingRepository.findDuoMatching(apiCaller);

        if (matching.getMatchingStatus().equals(LolMatchingStatus.MATCHING)){
            throw new BadRequestException("이미 방에 입장");
        }

        LolRequest request = lolRequestRepository.findRequest(apiCaller, matching.getRespondent());
        lolRequestRepository.updateRequestStatus(request, LolRequestStatus.FINISHED);

        lolRequestRepository.updateAllSentRequest(matching.getRequester(), LolRequestStatus.CANCELED);
        lolRequestRepository.updateAllSentRequest(matching.getRespondent(), LolRequestStatus.CANCELED);

        userRepository.updateUserStatus(apiCaller, UserStatus.LOL_DUO_MATCHING);
        lolMatchingRepository.setDuoMatchingStatus(matching, LolMatchingStatus.MATCHING);

        // fcm 관련 로직
    }
}
