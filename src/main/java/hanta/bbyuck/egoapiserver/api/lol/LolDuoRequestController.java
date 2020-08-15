package hanta.bbyuck.egoapiserver.api.lol;

import com.sun.org.apache.xpath.internal.objects.XNull;
import hanta.bbyuck.egoapiserver.request.lol.LolDuoRequestDto;
import hanta.bbyuck.egoapiserver.request.lol.LolDuoRequestGetDto;
import hanta.bbyuck.egoapiserver.response.ResponseMessage;
import hanta.bbyuck.egoapiserver.response.lol.LolProcessedDuoProfileCardDeck;
import hanta.bbyuck.egoapiserver.response.lol.LolRequestDuoProfileCardDeck;
import hanta.bbyuck.egoapiserver.service.lol.LolDuoRequestService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.spel.ast.NullLiteral;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class LolDuoRequestController {
    private final LolDuoRequestService lolDuoRequestService;

    @ApiOperation(value = "롤 듀오 신청",
            notes = "롤 듀오 신청 보내기 API\n" +
                    "1. 듀오 신청을 보낸 시점에서 유저가 이미 매칭이 성사 되었으면(status 변경) 실패 response 반납\n" +
                    "2. 듀오 신청을 보낸 시점에서 유저가 이미 MAX_REQUEST_COUNT만큼의 요청을 받은 상태라면 실패 response 반납\n" +
                    "3. 해당 실패 response 받으면 클라이언트단에서 해당 유저 카드를 현재 보여지는 덱에서 빼고 한 명의 유저를 추가로 보여줌\n" +
                    "4. 만일 클라이언트로 받은 모든 유저덱을 소진했다면 새로운 프로필카드 덱 요청")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "senderAuth", value = "유저 인증 정보", defaultValue = "sdsnadnsao21n3o1ni3o1"),
            @ApiImplicitParam(name = "receiverProfileCardId", value = "요청받는 유저의 프로필 카드 id (Number 숫자타입)", defaultValue = "13")
    })
    @PostMapping("/api/v0.0.1/lol/duo/match-request")
    public ResponseMessage sendRequest(@RequestBody LolDuoRequestDto lolDuoRequestDto) {
        lolDuoRequestService.sendRequest(lolDuoRequestDto);
        return new ResponseMessage("Match Request Send API Call Success!");
    }


    @ApiOperation(value = "롤 듀오 보낸 신청 목록",
            notes = "롤 듀오 보낸 신청 목록 가져오기 API\n" +
                    "1. 신청을 받은 유저가 프로필 카드 수정시 변경 사항 적용\n" +
                    "2. 현재 MAX_REQUEST_COUNT = 5\n" +
                    "3. 신청 받은 유저 정보 대신 유저의 프로필 카드 정보 리턴" +
                    "4. 가공 처리 된 프로필 카드 정보(아이디 가려짐)",
            response = LolRequestDuoProfileCardDeck.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userAuth", value = "유저 인증 정보", defaultValue = "sdsnadnsao21n3o1ni3o1"),
    })
    @GetMapping("api/v0.0.1/lol/duo/sent-request")
    public ResponseMessage getSentRequest(@RequestBody LolDuoRequestGetDto requestDto) {
        LolRequestDuoProfileCardDeck sentRequestDeck = lolDuoRequestService.getSendRequest(requestDto);
        return new ResponseMessage("Sent Request List Get API Call Success", sentRequestDeck);
    }

    @ApiOperation(value = "롤 듀오 받은 신청 목록",
            notes = "롤 듀오 받은 신청 목록 가져오기 API\n" +
                    "1. 신청을 받은 유저가 프로필 카드 수정시 변경 사항 적용\n" +
                    "2. 현재 MAX_REQUEST_COUNT = 5\n" +
                    "3. 신청 보낸 유저 정보 대신 유저의 프로필 카드 정보 리턴" +
                    "4. 가공 처리 된 프로필 카드 정보(아이디 가려짐)",
            response = LolRequestDuoProfileCardDeck.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userAuth", value = "유저 인증 정보", defaultValue = "sdsnadnsao21n3o1ni3o1"),
    })
    @GetMapping("api/v0.0.1/lol/duo/received-request")
    public ResponseMessage getReceivedRequest(@RequestBody LolDuoRequestGetDto requestDto) {
        LolRequestDuoProfileCardDeck receivedRequestDeck = lolDuoRequestService.getReceiveRequest(requestDto);
        return new ResponseMessage("Sent Request List Get API Call Success", receivedRequestDeck);
    }
}
