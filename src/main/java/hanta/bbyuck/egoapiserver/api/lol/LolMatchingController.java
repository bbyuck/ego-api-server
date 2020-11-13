package hanta.bbyuck.egoapiserver.api.lol;

import hanta.bbyuck.egoapiserver.request.lol.LolMatchingRequestDto;
import hanta.bbyuck.egoapiserver.request.lol.LolReportRequestDto;
import hanta.bbyuck.egoapiserver.response.ResponseMessage;
import hanta.bbyuck.egoapiserver.response.lol.LolMatchingResponseDto;
import hanta.bbyuck.egoapiserver.response.lol.LolProcessedProfileCardDeck;
import hanta.bbyuck.egoapiserver.service.EgoScoreService;
import hanta.bbyuck.egoapiserver.service.lol.LolMatchingService;
import hanta.bbyuck.egoapiserver.service.lol.LolReportService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/user/api/v0.0.1/lol/duo")
public class LolMatchingController {

    private final LolMatchingService lolMatchingService;
    private final LolReportService lolReportService;
    private final EgoScoreService egoScoreService;

    @ApiOperation(value = "현재 참여하고 있는 매칭 정보 조회",
            notes = "매칭이 끝날 때 까지는 세션 스토리지 저장 -> 만약 그 전에 매칭이 종료되지 않는다면 클라이언트에서 재요청\n" +
                    "1. 본인이 아닌 사람이 요청시(토큰이 없는 유저) 에러 발생 -> 로그인 필요\n" +
                    "2. 만약 한 명이 삭제하고 다른 한 명은 매칭창을 유지하는 경우 예외처리 필요")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "generatedId", value = "회원가입 및 로그인시 제공받은 Id", defaultValue = "sdsnadnsao21n3o1ni3o1"),
            @ApiImplicitParam(name = "clientVersion", value = "클라이언트 애플리케이션 버전", defaultValue = "v1.00"),
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping("/match")
    public ResponseMessage getMyInProgressMatching(@RequestBody LolMatchingRequestDto requestDto) {
        LolMatchingResponseDto responseDto = lolMatchingService.findMatch(requestDto);
        return new ResponseMessage("Get Duo Matching info API Call Success!","LDM-OBJ-001" ,responseDto);
    }

    @ApiOperation(value = "매칭 수락",
            notes = "매칭 수락시 이용할 API 요청을 보낸 유저와 요청을 받은 유저의 매칭이 생성\n" +
                    "1. 매칭시 유저 상태가 변경\n",
            response = LolProcessedProfileCardDeck.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "generatedId", value = "회원가입 및 로그인시 제공받은 Id", defaultValue = "sdsnadnsao21n3o1ni3o1"),
            @ApiImplicitParam(name = "clientVersion", value = "클라이언트 애플리케이션 버전", defaultValue = "v1.00"),
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PostMapping("/match")
    public ResponseMessage acceptMatch(@RequestBody LolMatchingRequestDto requestDto) {
        lolMatchingService.match(requestDto);
        return new ResponseMessage("Duo Match API Call Success!", "LDM-NONE-001");

    }

    @ApiOperation(value = "매칭방 입장",
            notes = "requester 매칭방 입장")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "generatedId", value = "회원가입 및 로그인시 제공받은 Id", defaultValue = "sdsnadnsao21n3o1ni3o1"),
            @ApiImplicitParam(name = "clientVersion", value = "클라이언트 애플리케이션 버전", defaultValue = "v1.00"),
            @ApiImplicitParam(name = "opponentProfileCardId", example = "12"),
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PutMapping("/match")
    public ResponseMessage enterMatch(@RequestBody LolMatchingRequestDto requestDto) {
        lolMatchingService.enterMatch(requestDto);
        return new ResponseMessage("매칭방 입장", "LDM-NONE-002");
    }

    @ApiOperation(value = "개별 매칭 종료",
            notes = "매칭 상태에 따른 작동이 다름\n" +
                    "1. 매칭상태 : MATCHING_ON -> 요청 수락한 유저(respondent)가 임의로 종료 -> 요청 보낸 유저(requester)상태 ACTIVE로 변경\n" +
                    "2. 매칭상태 : MATCHING -> 매칭에 참여한 유저 둘 중 아무나 종료 -> 유저 상태 둘다 변경필요\n" +
                    "3. 매칭상태 : MATCHING_OFF -> 매칭상태만 MATCHING_FINISH로 저장하고 평가 테이블로")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "generatedId", value = "회원가입 및 로그인시 제공받은 Id", defaultValue = "sdsnadnsao21n3o1ni3o1"),
            @ApiImplicitParam(name = "clientVersion", value = "클라이언트 애플리케이션 버전", defaultValue = "v1.00"),
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @DeleteMapping("/match")
    public ResponseMessage completeMatch(@RequestBody LolMatchingRequestDto requestDto) {
        lolMatchingService.completeMatch(requestDto);
        return new ResponseMessage("Complete Duo Match API Call Success!", "LDM-NONE-003");
    }


    @PostMapping("/match/report")
    public ResponseMessage reportOpponent(@RequestBody LolReportRequestDto requestDto) {
        lolReportService.reportOpponent(requestDto);
        return new ResponseMessage("신고 완료", "LDM-NONE-006");
    }
}

