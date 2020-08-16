package hanta.bbyuck.egoapiserver.service.lol;

import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.domain.lol.LolDuoInProgressMatching;
import hanta.bbyuck.egoapiserver.repository.UserRepository;
import hanta.bbyuck.egoapiserver.repository.lol.LolDuoInProgressMatchingRepository;
import hanta.bbyuck.egoapiserver.repository.lol.LolDuoProfileCardRepository;
import hanta.bbyuck.egoapiserver.request.lol.LolDuoMatchingRequestDto;
import hanta.bbyuck.egoapiserver.util.ClientVersionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LolDuoInProgressMatchingService {
    private final LolDuoInProgressMatchingRepository lolDuoInProgressMatchingRepository;
    private final LolDuoProfileCardRepository lolDuoProfileCardRepository;
    private final UserRepository userRepository;
    private final ClientVersionManager clientVersionManager;

    public void match(LolDuoMatchingRequestDto requestDto) {
        User reqUser = userRepository.find(requestDto.getUserAuth());
        User opponent = lolDuoProfileCardRepository.findById(requestDto.getOpponentProfileCardId()).getOwner();

        LolDuoInProgressMatching matching = new LolDuoInProgressMatching();

    }
}
