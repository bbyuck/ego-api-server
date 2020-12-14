package hanta.bbyuck.egoapiserver.service.lol;

import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.domain.enumset.UserStatus;
import hanta.bbyuck.egoapiserver.domain.lol.LolProfileCard;
import hanta.bbyuck.egoapiserver.domain.lol.LolRequest;
import hanta.bbyuck.egoapiserver.domain.lol.enumset.LolRequestStatus;
import hanta.bbyuck.egoapiserver.domain.lol.enumset.LolRequestType;
import hanta.bbyuck.egoapiserver.exception.BadMatchRequestException;
import hanta.bbyuck.egoapiserver.exception.SendRequestExhaustedException;
import hanta.bbyuck.egoapiserver.repository.UserRepository;
import hanta.bbyuck.egoapiserver.repository.lol.LolProfileCardRepository;
import hanta.bbyuck.egoapiserver.repository.lol.LolRequestRepository;
import hanta.bbyuck.egoapiserver.request.lol.LolRequestDto;
import hanta.bbyuck.egoapiserver.request.lol.LolRequestGetDto;
import hanta.bbyuck.egoapiserver.response.lol.LolRequestProfileCard;
import hanta.bbyuck.egoapiserver.response.lol.LolRequestProfileCardDeck;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static hanta.bbyuck.egoapiserver.util.ClientVersionManager.*;
import static hanta.bbyuck.egoapiserver.util.ServiceUtil.addCardToRequestDeck;

@Service
@RequiredArgsConstructor
public class LolRequestService {
    private final LolRequestRepository lolRequestRepository;
    private final LolProfileCardRepository lolProfileCardRepository;
    private final UserRepository userRepository;

    private static final int MAX_REQUEST_COUNT = 5;

    public void sendRequest(LolRequestDto requestDto) {
        checkClientVersion(requestDto.getClientVersion());

        LolRequest duoRequest = new LolRequest();

        // receiver card의 match type 따라서 (DUO OR TEAM) 요청 횟수 다르게
        User sender = userRepository.find(requestDto.getGeneratedId());
        LolProfileCard receiverCard = lolProfileCardRepository.findById(requestDto.getOpponentProfileCardId());
        User receiver = receiverCard.getOwner();

        if(sender == receiver) {
            throw new BadMatchRequestException("본인에게는 요청을 보낼 수 없습니다.");
        }

        // 보낼 수 있는 요청횟수를 모두 사용
        if (lolRequestRepository.findSend(sender).size() >= MAX_REQUEST_COUNT) throw new SendRequestExhaustedException();

        // 유저 상태가 맞지 않음
        if (sender.getStatus() != UserStatus.ACTIVE && receiver.getStatus() != UserStatus.ACTIVE) throw new BadMatchRequestException();

        // 이미 둘 사이에 요청이 생성되어 있는 경우
        if(lolRequestRepository.isExistRequest(sender, receiver) ||
                lolRequestRepository.isExistRequest(receiver, sender)) throw new BadMatchRequestException();

        duoRequest.assignSender(sender);
        duoRequest.assignReceiver(receiver);
        duoRequest.updateRequestTime();
        duoRequest.setStatus(LolRequestStatus.ACTIVE);
        duoRequest.setType(LolRequestType.NORMAL);

        lolRequestRepository.save(duoRequest);
    }

    /*
     * 내가 받은 요청 거절 (메서드 호출자가 receiver)
     */
    public void rejectRequest(LolRequestDto requestDto) {
        checkClientVersion(requestDto.getClientVersion());

       User reqUser = userRepository.find(requestDto.getGeneratedId());
       User opponent = lolProfileCardRepository.findById(requestDto.getOpponentProfileCardId()).getOwner();
       
       LolRequest request = lolRequestRepository.findRequest(opponent, reqUser);
       
       lolRequestRepository.updateRequestStatus(request, LolRequestStatus.DELETED);
    }

    /*
     * 내가 보낸 요청 취소 (메서드 호출자가 sender)
     */
    public void cancelRequest(LolRequestDto requestDto) {
        checkClientVersion(requestDto.getClientVersion());

        User reqUser = userRepository.find(requestDto.getGeneratedId());
        User opponent = lolProfileCardRepository.findById(requestDto.getOpponentProfileCardId()).getOwner();

        LolRequest request = lolRequestRepository.findRequest(reqUser, opponent);

        lolRequestRepository.updateRequestStatus(request, LolRequestStatus.DELETED);
    }

    public void rejectAllRequest(LolRequestDto requestDto) {
        checkClientVersion(requestDto.getClientVersion());

        User reqUser = userRepository.find(requestDto.getGeneratedId());
        lolRequestRepository.updateAllReceivedRequest(reqUser, LolRequestStatus.DELETED);
    }

    public void cancelAllRequest(LolRequestDto requestDto) {
        checkClientVersion(requestDto.getClientVersion());

        User reqUser = userRepository.find(requestDto.getGeneratedId());
        lolRequestRepository.updateAllSentRequest(reqUser, LolRequestStatus.DELETED);
    }

    // 메소드 호출한 유저가 sender
    public LolRequestProfileCardDeck getSendRequest(LolRequestGetDto requestDto) {
        checkClientVersion(requestDto.getClientVersion());

        User reqUser = userRepository.find(requestDto.getUserAuth());
        List<LolRequest> sentRequestList = lolRequestRepository.findSend(reqUser);

        LolRequestProfileCardDeck sentRequestDeck = new LolRequestProfileCardDeck();
        List<LolRequestProfileCard> cards = new ArrayList<>();

        for (LolRequest sentRequest : sentRequestList) {
            User receiver = sentRequest.getReceiver();
            LolProfileCard receiverProfile = lolProfileCardRepository.find(receiver);
            addCardToRequestDeck(cards, receiverProfile, sentRequest.getRequest_time());
        }
        sentRequestDeck.setMakeTime(LocalDateTime.now());
        sentRequestDeck.setCardCount(cards.size());
        sentRequestDeck.setDuoRequestProfileCards(cards);

        return sentRequestDeck;
    }

    // method 호출한 유저가 receiver
    public LolRequestProfileCardDeck getReceiveRequest(LolRequestGetDto requestDto) {
        checkClientVersion(requestDto.getClientVersion());


        User reqUser = userRepository.find(requestDto.getUserAuth());
        List<LolRequest> receivedRequestList = lolRequestRepository.findReceive(reqUser);

        LolRequestProfileCardDeck receivedRequestDeck = new LolRequestProfileCardDeck();
        List<LolRequestProfileCard> cards = new ArrayList<>();

        for (LolRequest receivedRequest : receivedRequestList) {
            User sender = receivedRequest.getSender();
            LolProfileCard receiverProfile = lolProfileCardRepository.find(sender);
            addCardToRequestDeck(cards, receiverProfile, receivedRequest.getRequest_time());
        }
        receivedRequestDeck.setMakeTime(LocalDateTime.now());
        receivedRequestDeck.setCardCount(cards.size());
        receivedRequestDeck.setDuoRequestProfileCards(cards);

        return receivedRequestDeck;
    }
}