package hanta.bbyuck.egoapiserver.service.lol;

import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.domain.enumset.UserStatus;
import hanta.bbyuck.egoapiserver.domain.lol.LolProfileCard;
import hanta.bbyuck.egoapiserver.domain.lol.LolRequest;
import hanta.bbyuck.egoapiserver.domain.lol.enumset.LolRequestStatus;
import hanta.bbyuck.egoapiserver.domain.enumset.RequestType;
import hanta.bbyuck.egoapiserver.exception.BadMatchRequestException;
import hanta.bbyuck.egoapiserver.exception.RecommendRequestOverException;
import hanta.bbyuck.egoapiserver.exception.SendRequestExhaustedException;
import hanta.bbyuck.egoapiserver.repository.UserRepository;
import hanta.bbyuck.egoapiserver.repository.lol.LolProfileCardRepository;
import hanta.bbyuck.egoapiserver.repository.lol.LolRequestRepository;
import hanta.bbyuck.egoapiserver.request.lol.LolRequestDto;
import hanta.bbyuck.egoapiserver.request.lol.LolRequestGetDto;
import hanta.bbyuck.egoapiserver.response.lol.LolProfileCardDeckDto;
import hanta.bbyuck.egoapiserver.response.lol.LolProfileCardResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static hanta.bbyuck.egoapiserver.util.ClientVersionManager.*;
import static hanta.bbyuck.egoapiserver.util.ServiceUtil.addCardToDeck;

/*
 * <pre>
 * Copyright (c) 2020 HANTA
 * All rights reserved.
 *
 * This software is the proprietary information of HANTA
 * </pre>
 *
 * @ author 강혁(bbyuck) (k941026h@naver.com)
 * @ since  2020. 01. 01
 *
 * @History
 * <pre>
 * -----------------------------------------------------
 * 2020.01.01
 * bbyuck (k941026h@naver.com) 최초작성
 * -----------------------------------------------------
 * </pre>
 */

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
        LolProfileCard receiverCard = lolProfileCardRepository.findById(requestDto.getOpponentProfileId());
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

        // 추천을 받은 유저에 요청을 보낼 때 이미 추천 유저에게 보낸 요청이 남아 있는지 확인
        LocalDateTime today = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        if (requestDto.getRequestType() == RequestType.RECOMMENDED && lolRequestRepository.isExistActiveRecommendedRequest(today)) throw new RecommendRequestOverException();


        duoRequest.assignSender(sender);
        duoRequest.assignReceiver(receiver);
        duoRequest.updateRequestTime();
        duoRequest.setStatus(LolRequestStatus.ACTIVE);
        duoRequest.setType(requestDto.getRequestType());

        lolRequestRepository.save(duoRequest);
    }

    /*
     * 내가 받은 요청 거절 (메서드 호출자가 receiver)
     */
    public void rejectRequest(LolRequestDto requestDto) {
        checkClientVersion(requestDto.getClientVersion());

       User reqUser = userRepository.find(requestDto.getGeneratedId());
       User opponent = lolProfileCardRepository.findById(requestDto.getOpponentProfileId()).getOwner();
       
       LolRequest request = lolRequestRepository.findRequest(opponent, reqUser);
       
       lolRequestRepository.updateRequestStatus(request, LolRequestStatus.DELETED);
    }

    /*
     * 내가 보낸 요청 취소 (메서드 호출자가 sender)
     */
    public void cancelRequest(LolRequestDto requestDto) {
        checkClientVersion(requestDto.getClientVersion());

        User reqUser = userRepository.find(requestDto.getGeneratedId());
        User opponent = lolProfileCardRepository.findById(requestDto.getOpponentProfileId()).getOwner();

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
    public LolProfileCardDeckDto getSendRequest(LolRequestGetDto requestDto) {
        checkClientVersion(requestDto.getClientVersion());

        User apiCaller = userRepository.find(requestDto.getGeneratedId());
        List<LolRequest> sentRequestList = lolRequestRepository.findSend(apiCaller);

        LolProfileCardDeckDto sentRequestDeck = new LolProfileCardDeckDto();
        List<LolProfileCardResponseDto> cards = new ArrayList<>();

        for (LolRequest sentRequest : sentRequestList) {
            User receiver = sentRequest.getReceiver();
            LolProfileCard receiverProfile = lolProfileCardRepository.find(receiver);
            addCardToDeck(cards, receiverProfile);
        }
        sentRequestDeck.setMakeTime(LocalDateTime.now());
        sentRequestDeck.setCardCount(cards.size());
        sentRequestDeck.setDuoProfileCards(cards);

        return sentRequestDeck;
    }

    public Integer getSentRequestCount(LolRequestGetDto requestDto) {
        User apiCaller = userRepository.find(requestDto.getGeneratedId());
        List<LolRequest> sentRequests = lolRequestRepository.findSend(apiCaller);

        return sentRequests.size();
    }

    // method 호출한 유저가 receiver
    public LolProfileCardDeckDto getReceiveRequest(LolRequestGetDto requestDto) {
        checkClientVersion(requestDto.getClientVersion());


        User reqUser = userRepository.find(requestDto.getGeneratedId());
        List<LolRequest> receivedRequestList = lolRequestRepository.findReceive(reqUser);

        LolProfileCardDeckDto receivedRequestDeck = new LolProfileCardDeckDto();
        List<LolProfileCardResponseDto> cards = new ArrayList<>();

        for (LolRequest receivedRequest : receivedRequestList) {
            User sender = receivedRequest.getSender();
            LolProfileCard receiverProfile = lolProfileCardRepository.find(sender);
            addCardToDeck(cards, receiverProfile);
        }
        receivedRequestDeck.setMakeTime(LocalDateTime.now());
        receivedRequestDeck.setCardCount(cards.size());
        receivedRequestDeck.setDuoProfileCards(cards);

        return receivedRequestDeck;
    }
}
