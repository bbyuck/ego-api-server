package hanta.bbyuck.egoapiserver.service.lol;

import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.domain.enumset.UserStatus;
import hanta.bbyuck.egoapiserver.domain.lol.LolDuoProfileCard;
import hanta.bbyuck.egoapiserver.domain.lol.LolDuoRequest;
import hanta.bbyuck.egoapiserver.domain.lol.enumset.LolRequestStatus;
import hanta.bbyuck.egoapiserver.domain.lol.enumset.LolRequestType;
import hanta.bbyuck.egoapiserver.exception.BadMatchRequestException;
import hanta.bbyuck.egoapiserver.exception.SendRequestExhaustedException;
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

import static hanta.bbyuck.egoapiserver.util.ClientVersionManager.*;
import static hanta.bbyuck.egoapiserver.util.ServiceUtil.addCardToRequestDeck;

@Service
@RequiredArgsConstructor
public class LolDuoRequestService {
    private final LolDuoRequestRepository lolDuoRequestRepository;
    private final LolDuoProfileCardRepository lolDuoProfileCardRepository;
    private final UserRepository userRepository;

    private static final int MAX_REQUEST_COUNT = 5;

    public void sendRequest(LolDuoRequestDto requestDto) {
        checkClientVersion(requestDto.getClientVersion());

        LolDuoRequest duoRequest = new LolDuoRequest();

        User sender = userRepository.find(requestDto.getGeneratedId());
        User receiver = lolDuoProfileCardRepository.findById(requestDto.getOpponentProfileCardId()).getOwner();

        if(sender == receiver) {
            throw new BadMatchRequestException("본인에게는 요청을 보낼 수 없습니다.");
        }

        // 보낼 수 있는 요청횟수를 모두 사용
        if (lolDuoRequestRepository.findSend(sender).size() >= MAX_REQUEST_COUNT) throw new SendRequestExhaustedException();


        // 유저 상태가 맞지 않음
        if (sender.getStatus() != UserStatus.ACTIVE || receiver.getStatus() != UserStatus.ACTIVE) throw new BadMatchRequestException();


        // 이미 둘 사이에 요청이 생성되어 있는 경우
        if(lolDuoRequestRepository.isExistRequest(sender, receiver) ||
                lolDuoRequestRepository.isExistRequest(receiver, sender)) throw new BadMatchRequestException();

        duoRequest.assignSender(sender);
        duoRequest.assignReceiver(receiver);
        duoRequest.updateRequestTime();
        duoRequest.setStatus(LolRequestStatus.ACTIVE);
        duoRequest.setType(LolRequestType.NORMAL);

        lolDuoRequestRepository.save(duoRequest);
    }

    /*
     * 내가 받은 요청 거절 (메서드 호출자가 receiver)
     */
    public void rejectRequest(LolDuoRequestDto requestDto) {
        checkClientVersion(requestDto.getClientVersion());


       User reqUser = userRepository.find(requestDto.getGeneratedId());
       User opponent = lolDuoProfileCardRepository.findById(requestDto.getOpponentProfileCardId()).getOwner();
       
       LolDuoRequest request = lolDuoRequestRepository.findRequest(opponent, reqUser);
       
       lolDuoRequestRepository.updateRequestStatus(request, LolRequestStatus.DELETED);
    }

    /*
     * 내가 보낸 요청 취소 (메서드 호출자가 sender)
     */
    public void cancelRequest(LolDuoRequestDto requestDto) {
        checkClientVersion(requestDto.getClientVersion());

        User reqUser = userRepository.find(requestDto.getGeneratedId());
        User opponent = lolDuoProfileCardRepository.findById(requestDto.getOpponentProfileCardId()).getOwner();

        LolDuoRequest request = lolDuoRequestRepository.findRequest(reqUser, opponent);

        lolDuoRequestRepository.updateRequestStatus(request, LolRequestStatus.DELETED);
    }

    public void rejectAllRequest(LolDuoRequestDto requestDto) {
        checkClientVersion(requestDto.getClientVersion());

        User reqUser = userRepository.find(requestDto.getGeneratedId());
        lolDuoRequestRepository.updateAllReceivedRequest(reqUser, LolRequestStatus.DELETED);
    }

    public void cancelAllRequest(LolDuoRequestDto requestDto) {
        checkClientVersion(requestDto.getClientVersion());

        User reqUser = userRepository.find(requestDto.getGeneratedId());
        lolDuoRequestRepository.updateAllSentRequest(reqUser, LolRequestStatus.DELETED);
    }

    // 메소드 호출한 유저가 sender
    public LolRequestDuoProfileCardDeck getSendRequest(LolDuoRequestGetDto requestDto) {
        checkClientVersion(requestDto.getClientVersion());

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
        checkClientVersion(requestDto.getClientVersion());


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
