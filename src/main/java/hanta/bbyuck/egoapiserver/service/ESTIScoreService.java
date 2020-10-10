package hanta.bbyuck.egoapiserver.service;

import hanta.bbyuck.egoapiserver.repository.ESTIScoreRepository;
import hanta.bbyuck.egoapiserver.request.lol.LolDuoMatchingRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ESTIScoreService {
    private final ESTIScoreRepository estiScoreRepository;

    public void evaluateOpponent(LolDuoMatchingRequestDto requestDto) {

    }
}
