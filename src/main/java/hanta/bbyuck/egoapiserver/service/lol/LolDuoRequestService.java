package hanta.bbyuck.egoapiserver.service.lol;

import hanta.bbyuck.egoapiserver.repository.lol.LolDuoRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LolDuoRequestService {
    private final LolDuoRequestRepository lolDuoRequestRepository;
}
