package hanta.bbyuck.egoapiserver.service.lol;

import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.domain.UserStatus;
import hanta.bbyuck.egoapiserver.domain.lol.LolDuoProfileCard;
import hanta.bbyuck.egoapiserver.domain.lol.LolDuoRequest;
import hanta.bbyuck.egoapiserver.exception.http.BadRequestException;
import hanta.bbyuck.egoapiserver.repository.UserRepository;
import hanta.bbyuck.egoapiserver.repository.lol.LolDuoProfileCardRepository;
import hanta.bbyuck.egoapiserver.repository.lol.LolDuoRequestRepository;
import hanta.bbyuck.egoapiserver.request.lol.LolDuoRequestDto;
import hanta.bbyuck.egoapiserver.request.lol.LolDuoRequestGetDto;
import hanta.bbyuck.egoapiserver.response.lol.LolRequestDuoProfileCard;
import hanta.bbyuck.egoapiserver.response.lol.LolRequestDuoProfileCardDeck;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

        if(sender == receiver) {
            throw new BadRequestException("본인에게는 요청을 보낼 수 없습니다.");
        }

        if (lolDuoRequestRepository.findSend(sender).size() >= MAX_REQUEST_COUNT) {
            throw new BadRequestException("보낼 수 있는 요청 횟수를 모두 사용했습니다.");
        }
        if (lolDuoRequestRepository.findReceive(receiver).size() >= MAX_REQUEST_COUNT) {
            throw new BadRequestException("상대방이 받을 수 있는 요청 횟수를 모두 사용했습니다.");
        }


        if (sender.getStatus() != UserStatus.ACTIVE && sender.getStatus() != UserStatus.INACTIVE) {
            throw new BadRequestException("유저 상태가 맞지 않습니다.");
        }
        if (receiver.getStatus() != UserStatus.ACTIVE && receiver.getStatus() != UserStatus.INACTIVE) {
            throw new BadRequestException("유저 상태가 맞지 않습니다.");
        }

        if(lolDuoRequestRepository.isExistRequest(sender, receiver) > 0) {
            throw new BadRequestException("이미 요청을 보낸 소환사입니다.");
        }

        duoRequest.assignSender(sender);
        duoRequest.assignReceiver(receiver);
        duoRequest.updateRequestTime();

        lolDuoRequestRepository.save(duoRequest);
    }

    // 메소드 호출한 유저가 sender
    public LolRequestDuoProfileCardDeck getSendRequest(LolDuoRequestGetDto requestDto) {
        User reqUser = userRepository.find(requestDto.getUserAuth());
        List<LolDuoRequest> sentRequestList = lolDuoRequestRepository.findSend(reqUser);

        LolRequestDuoProfileCardDeck sentRequestDeck = new LolRequestDuoProfileCardDeck();
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
    public LolRequestDuoProfileCardDeck getReceiveRequest(LolDuoRequestGetDto requestDto) {
        User reqUser = userRepository.find(requestDto.getUserAuth());
        List<LolDuoRequest> receivedRequestList = lolDuoRequestRepository.findReceive(reqUser);

        LolRequestDuoProfileCardDeck receivedRequestDeck = new LolRequestDuoProfileCardDeck();
        List<LolRequestDuoProfileCard> cards = new ArrayList<>();

        for (LolDuoRequest receivedRequest : receivedRequestList) {
            User sender = receivedRequest.getSender();
            LolDuoProfileCard receiverProfile = lolDuoProfileCardRepository.find(sender);
            addCardToRequestDeck(cards, receiverProfile, receivedRequest.getRequest_time());
        }
        receivedRequestDeck.setMakeTime(LocalDateTime.now());
        receivedRequestDeck.setCardCount(cards.size());
        receivedRequestDeck.setDuoRequestProfileCards(cards);

        return receivedRequestDeck;
    }


}