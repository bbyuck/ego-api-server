package hanta.bbyuck.egoapiserver.service;

import hanta.bbyuck.egoapiserver.domain.EgoScore;
import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.domain.enumset.Game;
import hanta.bbyuck.egoapiserver.domain.enumset.GameType;
import hanta.bbyuck.egoapiserver.domain.enumset.UserStatus;
import hanta.bbyuck.egoapiserver.exception.http.BadRequestException;
import hanta.bbyuck.egoapiserver.repository.EgoScoreRepository;
import hanta.bbyuck.egoapiserver.repository.UserRepository;
import hanta.bbyuck.egoapiserver.repository.lol.LolDuoProfileCardRepository;
import hanta.bbyuck.egoapiserver.request.EgoScoreRequestDto;
import hanta.bbyuck.egoapiserver.util.ClientVersionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static hanta.bbyuck.egoapiserver.domain.enumset.GameType.DUO;

@Service
@RequiredArgsConstructor
public class EgoScoreService {
    private final EgoScoreRepository egoScoreRepository;
    private final UserRepository userRepository;
    private final LolDuoProfileCardRepository lolDuoProfileCardRepository;
    private final Integer DEFAULT_POINT = 1;

    public void evaluateOpponent(EgoScoreRequestDto requestDto) {
        ClientVersionManager.checkClientVersion(requestDto.getClientVersion());

        User apiCaller = userRepository.find(requestDto.getGeneratedId());

        if (!apiCaller.getStatus().equals(UserStatus.LOL_DUO_MATCHING_FINISH)) throw new BadRequestException();

        Game game = requestDto.getGame();
        User receiver = null;
        switch(game) {
            case LOL:
                if(requestDto.getGameType().equals(DUO)) receiver = lolDuoProfileCardRepository.findById(requestDto.getOpponentProfileCardId()).getOwner();
                break;
            default:
                break;
        }

        EgoScore score = new EgoScore();
        if (requestDto.getGood()) score.makeScore(apiCaller, receiver, game, requestDto.getGameType(), DEFAULT_POINT);
        else score.makeScore(apiCaller, receiver, game, requestDto.getGameType(),0);
        egoScoreRepository.save(score);

        userRepository.updateUserStatus(apiCaller, UserStatus.ACTIVE);
    }
}
