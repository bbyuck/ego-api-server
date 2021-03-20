package hanta.bbyuck.egoapiserver.service.lol;

import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.domain.lol.LolRecommendationRefresh;
import hanta.bbyuck.egoapiserver.repository.UserRepository;
import hanta.bbyuck.egoapiserver.repository.lol.LolProfileCardRepository;
import hanta.bbyuck.egoapiserver.repository.lol.LolRecommendationRefreshRepository;
import hanta.bbyuck.egoapiserver.request.lol.LolProfileCardRequestDto;
import hanta.bbyuck.egoapiserver.response.lol.LolProfileCardResponseDto;
import hanta.bbyuck.egoapiserver.util.TimeCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static hanta.bbyuck.egoapiserver.util.ClientVersionManager.*;

/*
 * <pre>
 * Copyright (c) 2020 HANTA
 * All rights reserved.
 *
 * This software is the proprietary information of HANTA
 * </pre>
 *
 * @ author 강혁(bbyuck) (k941026h@naver.com)
 * @ since  2020. 01. 01
 *
 * @History
 * <pre>
 * -----------------------------------------------------
 * 2020.01.01
 * bbyuck (k941026h@naver.com) 최초작성
 * -----------------------------------------------------
 * </pre>
 */

@Service
@RequiredArgsConstructor
public class LolRecommendationRefreshService {
    private final LolRecommendationRefreshRepository lolRecommendationRefreshRepository;
    private final UserRepository userRepository;
    private final LolProfileCardRepository lolProfileCardRepository;

    // 오늘 새로고침 횟수
    // 유저 컨트롤러에서 사용될 예정
    public Integer todayRefreshCount(LolProfileCardRequestDto requestDto) {
        checkClientVersion(requestDto.getClientVersion());

        User apiCaller = userRepository.find(requestDto.getGeneratedId());
        List<LolRecommendationRefresh> lateRefreshes = lolRecommendationRefreshRepository.find(apiCaller);

        return TimeCalculator.todayRefreshCount(lateRefreshes);
    }

    // 새로고침 여부 저장
    // 컨트롤러 통해서 바로 새로고침이 아니고 다른 서비스에서 사용될 예정
    // 프로필카드 컨트롤러의 기능 통해서 호출
    public void refresh(LolProfileCardRequestDto requestDto, LolProfileCardResponseDto profileCardDto) {
        LolRecommendationRefresh refresh = new LolRecommendationRefresh();
        User apiCaller = userRepository.find(requestDto.getGeneratedId());
        User opponent = lolProfileCardRepository.findById(profileCardDto.getProfileCardId()).getOwner();
        refresh.makeRefresh(apiCaller, opponent);
        lolRecommendationRefreshRepository.save(refresh);
    }

}
