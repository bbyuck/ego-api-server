package hanta.bbyuck.egoapiserver.api.lol;

import hanta.bbyuck.egoapiserver.domain.lol.LolDuoRequest;
import hanta.bbyuck.egoapiserver.request.lol.LolDuoRequestDto;
import hanta.bbyuck.egoapiserver.request.lol.LolDuoRequestGetDto;
import hanta.bbyuck.egoapiserver.response.ResponseMessage;
import hanta.bbyuck.egoapiserver.response.lol.LolDuoProfileCardDeck;
import hanta.bbyuck.egoapiserver.response.lol.LolProcessedDuoProfileCard;
import hanta.bbyuck.egoapiserver.service.lol.LolDuoRequestService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Controller
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
            @ApiImplicitParam(name = "userAuth", value = "유저 인증 정보", defaultValue = "sdsnadnsao21n3o1ni3o1"),
            @ApiImplicitParam(name = "receiverProfileCardId", value = "요청받는 유저의 프로필 카드 id (Number 숫자타입)", defaultValue = "13")
    })
    @PostMapping("/api/v0.1.1/lol/duo/match-request")
    public ResponseMessage sendRequest(@RequestBody LolDuoRequestDto lolDuoRequestDto) {
        lolDuoRequestService.sendRequest(lolDuoRequestDto);
        return new ResponseMessage("Match Request Send API Call Success!");
    }

    @GetMapping("api/v0.0.1/lol/duo/sent-request")
    public ResponseMessage getSentRequest(@RequestBody LolDuoRequestGetDto requestDto) {
        LolDuoProfileCardDeck sentRequestDeck = lolDuoRequestService.getSendRequest(requestDto);
        return new ResponseMessage("Sent Request List Get API Call Success", sentRequestDeck);
    }
}
