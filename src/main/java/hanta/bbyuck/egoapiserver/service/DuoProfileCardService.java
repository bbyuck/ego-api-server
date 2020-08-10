package hanta.bbyuck.egoapiserver.service;

import hanta.bbyuck.egoapiserver.domain.DuoProfileCard;
import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.repository.DuoProfileCardRepository;
import hanta.bbyuck.egoapiserver.repository.UserRepository;
import hanta.bbyuck.egoapiserver.request.DuoProfileCardMakeRequest;
import hanta.bbyuck.egoapiserver.util.AES256Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DuoProfileCardService {
    private final DuoProfileCardRepository duoProfileCardRepository;
    private final UserRepository userRepository;
    private final AES256Util aes256Util = AES256Util.getInstance();


    public Long makeDuoProfileCard(DuoProfileCardMakeRequest duoProfileCardMakeRequest) {
        try {
            Long id = Long.parseLong(aes256Util.AES_Decode(duoProfileCardMakeRequest.getOwnerAuth()));
            User owner = userRepository.find(id);

            DuoProfileCard duoProfileCard = new DuoProfileCard();
            duoProfileCard.makeProfileCard(owner,
                    duoProfileCardMakeRequest.getVoice(),
                    duoProfileCardMakeRequest.getSummonerName(),
                    duoProfileCardMakeRequest.getTier(),
                    duoProfileCardMakeRequest.getTierLev(),
                    duoProfileCardMakeRequest.getLp(),
                    duoProfileCardMakeRequest.getChampion1(),
                    duoProfileCardMakeRequest.getChampion2(),
                    duoProfileCardMakeRequest.getChampion3(),
                    duoProfileCardMakeRequest.getTop(),
                    duoProfileCardMakeRequest.getJungle(),
                    duoProfileCardMakeRequest.getMid(),
                    duoProfileCardMakeRequest.getAd(),
                    duoProfileCardMakeRequest.getSupport(),
                    duoProfileCardMakeRequest.getMainPosition());

            return duoProfileCardRepository.save(duoProfileCard);
        } catch (Exception e) {
            e.printStackTrace();
            return -1L;
        }


    }
}
