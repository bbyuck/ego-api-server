package hanta.bbyuck.egoapiserver.service.lol;

import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.domain.lol.LolDuoProfileCard;
import hanta.bbyuck.egoapiserver.domain.lol.LolDuoRequest;
import hanta.bbyuck.egoapiserver.repository.UserRepository;
import hanta.bbyuck.egoapiserver.repository.lol.LolDuoProfileCardRepository;
import hanta.bbyuck.egoapiserver.repository.lol.LolDuoRequestRepository;
import hanta.bbyuck.egoapiserver.request.lol.LolDuoRequestDto;
import hanta.bbyuck.egoapiserver.request.lol.LolDuoRequestGetDto;
import hanta.bbyuck.egoapiserver.response.lol.LolDuoProfileCardDeck;
import hanta.bbyuck.egoapiserver.response.lol.LolProcessedDuoProfileCard;
import hanta.bbyuck.egoapiserver.response.lol.LolRequestDuoProfileCard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static hanta.bbyuck.egoapiserver.util.ServiceUtil.addCardToProcessedDeck;
import static hanta.bbyuck.egoapiserver.util.ServiceUtil.addCardToRequestDeck;

@Service
@RequiredArgsConstructor
public class LolDuoRequestService {
    private final LolDuoRequestRepository lolDuoRequestRepository;
    private final LolDuoProfileCardRepository lolDuoProfileCardRepository;
    private final UserRepository userRepository;
    private static final int MAX_REQUEST_COUNT = 5;

    public void sendRequest(LolDuoRequestDto requestDto) {
        LolDuoRequest duoRequest = new LolDuoRequest();

        User sender = userRepository.find(requestDto.getSenderAuth());
        User receiver = lolDuoProfileCardRepository.findById(requestDto.getReceiverProfileCardId()).getOwner();

        duoRequest.assignSender(sender);
        duoRequest.assignReceiver(receiver);
        duoRequest.updateRequestTime();

        lolDuoRequestRepository.save(duoRequest);
    }

    // 메소드 호출한 유저가 sender
    public LolDuoProfileCardDeck getSendRequest(LolDuoRequestGetDto requestDto) {
        User reqUser = userRepository.find(requestDto.getUserAuth());
        List<LolDuoRequest> sentRequestList = lolDuoRequestRepository.findSend(reqUser);

        LolDuoProfileCardDeck sentRequestDeck = new LolDuoProfileCardDeck();
        List<LolRequestDuoProfileCard> cards = new ArrayList<>();

        for (LolDuoRequest sentRequest : sentRequestList) {
            User receiver = sentRequest.getReceiver();
            LolDuoProfileCard receiverProfile = lolDuoProfileCardRepository.find(receiver);
            addCardToRequestDeck(cards, receiverProfile, sentRequest.getRequest_time());
        }
        sentRequestDeck.setMakeTime(LocalDateTime.now());
        sentRequestDeck.setCardCount(cards.size());
        sentRequestDeck.setDuoRequestProfileCards(cards);

        return sentRequestDeck;
    }

    // method 호출한 유저가 receiver
    public LolDuoProfileCardDeck getReceiveRequest(LolDuoRequestGetDto requestDto) {
        User reqUser = userRepository.find(requestDto.getUserAuth());
        List<LolDuoRequest> receivedRequestList = lolDuoRequestRepository.findReceive(reqUser);

        LolDuoProfileCardDeck receivedRequestDeck = new LolDuoProfileCardDeck();
        List<LolRequestDuoProfileCard> cards = new ArrayList<>();

        for (LolDuoRequest receivedRequest : receivedRequestList) {
            User sender = receivedRequest.getReceiver();
            LolDuoProfileCard receiverProfile = lolDuoProfileCardRepository.find(sender);
            addCardToRequestDeck(cards, receiverProfile, receivedRequest.getRequest_time());
        }
        receivedRequestDeck.setMakeTime(LocalDateTime.now());
        receivedRequestDeck.setCardCount(cards.size());
        receivedRequestDeck.setDuoRequestProfileCards(cards);

        return receivedRequestDeck;
    }
}
