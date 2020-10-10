package hanta.bbyuck.egoapiserver.service.lol;

import hanta.bbyuck.egoapiserver.repository.lol.LolReportRepository;
import hanta.bbyuck.egoapiserver.request.lol.LolDuoMatchingRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LolReportService {
    private final LolReportRepository lolReportRepository;

    public void reportOpponent(LolDuoMatchingRequestDto requestDto) {
    }
}
