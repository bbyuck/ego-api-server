package hanta.bbyuck.egoapiserver.service;

import hanta.bbyuck.egoapiserver.domain.EgoTestAnswer;
import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.domain.UserType;
import hanta.bbyuck.egoapiserver.exception.http.BadRequestException;
import hanta.bbyuck.egoapiserver.repository.EgoTestRepository;
import hanta.bbyuck.egoapiserver.repository.UserRepository;
import hanta.bbyuck.egoapiserver.repository.UserTypeRepository;
import hanta.bbyuck.egoapiserver.request.EgoTestAnswerRequestDto;
import hanta.bbyuck.egoapiserver.response.EgoTestResponseDto;
import hanta.bbyuck.egoapiserver.util.ClientVersionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EgoTestService {
    private final EgoTestRepository egoTestRepository;
    private final UserRepository userRepository;
    private final UserTypeRepository userTypeRepository;

    // V1
    private String egoTestV1(EgoTestAnswerRequestDto requestDto) {
        String result = "";
        int leader = 0;
        int support = 0;
        int activist = 0;
        int theorist = 0;
        int bold = 0;
        int careful = 0;
        int combination = 0;
        int mastery = 0;
        
        // 1번
        if (requestDto.getAnswer_1()) leader++;
        else support++;

        // 2번
        if(requestDto.getAnswer_2()) support++;
        else leader++;

        // 3번
        if (requestDto.getAnswer_3()) {
            leader++;
            bold++;
        }
        else {
            support++;
            careful++;
        }

        // 4번
        if (requestDto.getAnswer_4()) activist++;
        else theorist++;

        // 5번
        if(requestDto.getAnswer_5()) activist++;
        else theorist++;

        // 6번
        if(requestDto.getAnswer_6()) activist++;
        else theorist++;

        // 7번
        if(requestDto.getAnswer_7()) bold++;
        else careful++;

        // 8번
        if(requestDto.getAnswer_8()) bold++;
        else careful++;

        // 9번
        if (requestDto.getAnswer_9()) bold++;
        else careful++;

        // 10번
        if(requestDto.getAnswer_10()) careful++;
        else bold++;

        // 11번
        if(requestDto.getAnswer_11()) mastery++;
        else combination++;

        // 12번
        if(requestDto.getAnswer_12()) mastery++;
        else combination++;

        // 13번
        if(requestDto.getAnswer_13()) combination++;
        else mastery++;
        
        if (leader > support) result += "L";
        else result += "S";
        
        if (activist > theorist) result += "A";
        else result += "T";
        
        if (bold > careful) result += "B";
        else result += "C";
        
        if (mastery > combination) result += "M";
        else result += "C";
        
        return result;
    }

    public EgoTestResponseDto saveAnswer(EgoTestAnswerRequestDto requestDto) {
        ClientVersionManager.checkClientVersion(requestDto.getClientVersion());
        EgoTestAnswer egoTestAnswer = new EgoTestAnswer();
        User respondent = userRepository.find(requestDto.getGeneratedId());
        Boolean more = false;
        // 이미 유저 성향을 제공받았다면 답안지는 저장하되 타입은 변동없음
        if (userTypeRepository.exist(respondent)) more = true;

        // null 체크
        checkRequest(requestDto);

        egoTestAnswer.fillAnswer(
                respondent,
                requestDto.getGame(),
                requestDto.getEgoTestVersion(),
                requestDto.getAnswer_1(),
                requestDto.getAnswer_2(),
                requestDto.getAnswer_3(),
                requestDto.getAnswer_4(),
                requestDto.getAnswer_5(),
                requestDto.getAnswer_6(),
                requestDto.getAnswer_7(),
                requestDto.getAnswer_8(),
                requestDto.getAnswer_9(),
                requestDto.getAnswer_10(),
                requestDto.getAnswer_11(),
                requestDto.getAnswer_12(),
                requestDto.getAnswer_13()
        );

        String type = "";
        switch(requestDto.getEgoTestVersion()) {
            case V1:
                type = egoTestV1(requestDto);
                break;
        }

        // 추가적인 테스트가 아니라면 새로운 타입을 유저에게 부여
        if (more) egoTestAnswer.setIsMain(false);
        else {
            egoTestAnswer.setIsMain(true);
            UserType userType = new UserType();
            userType.makeUserType(respondent, requestDto.getGame(), requestDto.getEgoTestVersion(), type);
            userTypeRepository.save(userType);
        }

        egoTestRepository.save(egoTestAnswer);


        EgoTestResponseDto egoTestResponseDto = new EgoTestResponseDto();
        egoTestResponseDto.setResult(type);

        return egoTestResponseDto;
    }


    public EgoTestResponseDto onlyTest(EgoTestAnswerRequestDto requestDto) {
        EgoTestResponseDto responseDto = new EgoTestResponseDto();
        responseDto.setResult(egoTestV1(requestDto));
        return responseDto;
    }

    /*
     * 체크메서드
     */
    private void checkRequest(EgoTestAnswerRequestDto requestDto) {
        if (requestDto.getAnswer_1() == null) throw new BadRequestException();
        if (requestDto.getAnswer_2() == null) throw new BadRequestException();
        if (requestDto.getAnswer_3() == null) throw new BadRequestException();
        if (requestDto.getAnswer_4() == null) throw new BadRequestException();
        if (requestDto.getAnswer_5() == null) throw new BadRequestException();
        if (requestDto.getAnswer_6() == null) throw new BadRequestException();
        if (requestDto.getAnswer_7() == null) throw new BadRequestException();
        if (requestDto.getAnswer_8() == null) throw new BadRequestException();
        if (requestDto.getAnswer_9() == null) throw new BadRequestException();
        if (requestDto.getAnswer_10() == null) throw new BadRequestException();
        if (requestDto.getAnswer_11() == null) throw new BadRequestException();
        if (requestDto.getAnswer_12() == null) throw new BadRequestException();
        if (requestDto.getAnswer_13() == null) throw new BadRequestException();
        if (requestDto.getEgoTestVersion() == null) throw new BadRequestException();
        if (requestDto.getGame() == null) throw new BadRequestException();
    }

}