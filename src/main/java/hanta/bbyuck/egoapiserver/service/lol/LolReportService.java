package hanta.bbyuck.egoapiserver.service.lol;

import hanta.bbyuck.egoapiserver.domain.EgoScore;
import hanta.bbyuck.egoapiserver.domain.User;

import hanta.bbyuck.egoapiserver.domain.enumset.GameType;
import hanta.bbyuck.egoapiserver.domain.enumset.MatchType;
import hanta.bbyuck.egoapiserver.domain.enumset.UserStatus;
import hanta.bbyuck.egoapiserver.domain.lol.LolReport;
import hanta.bbyuck.egoapiserver.exception.http.BadRequestException;
import hanta.bbyuck.egoapiserver.repository.EgoScoreRepository;
import hanta.bbyuck.egoapiserver.repository.UserRepository;
import hanta.bbyuck.egoapiserver.repository.lol.LolProfileCardRepository;
import hanta.bbyuck.egoapiserver.repository.lol.LolReportRepository;
import hanta.bbyuck.egoapiserver.request.lol.LolReportRequestDto;
import hanta.bbyuck.egoapiserver.util.ClientVersionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static hanta.bbyuck.egoapiserver.domain.enumset.Game.LOL;
import static hanta.bbyuck.egoapiserver.domain.enumset.MatchType.*;

@Service
@RequiredArgsConstructor
public class LolReportService {
    private final LolReportRepository lolReportRepository;
    private final EgoScoreRepository egoScoreRepository;
    private final UserRepository userRepository;
    private final LolProfileCardRepository lolProfileCardRepository;

    public void reportOpponent(LolReportRequestDto requestDto) {
        ClientVersionManager.checkClientVersion(requestDto.getClientVersion());

        User apiCaller = userRepository.find(requestDto.getGeneratedId());

        if (!apiCaller.getStatus().equals(UserStatus.LOL_DUO_MATCHING_FINISH)) throw new BadRequestException();

        User reported = lolProfileCardRepository.findById(requestDto.getOpponentProfileId()).getOwner();

        LolReport report = new LolReport();
        report.makeReport(apiCaller, reported, DUO, requestDto.getReportContent());

        lolReportRepository.save(report);
        EgoScore score = new EgoScore();

        score.makeScore(apiCaller, reported, LOL, DUO, 0);
        egoScoreRepository.save(score);

        userRepository.updateUserStatus(apiCaller, UserStatus.ACTIVE);
    }
}
