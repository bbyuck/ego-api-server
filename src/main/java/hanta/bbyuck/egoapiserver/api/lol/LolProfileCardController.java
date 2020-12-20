package hanta.bbyuck.egoapiserver.api.lol;

import hanta.bbyuck.egoapiserver.request.lol.LolMatchDeckRequestDto;
import hanta.bbyuck.egoapiserver.request.lol.LolProfileCardRequestDto;
import hanta.bbyuck.egoapiserver.request.lol.LolProfileCardMakeRequestDto;
import hanta.bbyuck.egoapiserver.request.lol.LolProfileCardUpdateRequestDto;
import hanta.bbyuck.egoapiserver.response.lol.LolProcessedProfileCardDeck;
import hanta.bbyuck.egoapiserver.response.ResponseMessage;
import hanta.bbyuck.egoapiserver.response.lol.LolProfileCardResponseDto;
import hanta.bbyuck.egoapiserver.service.lol.LolProfileCardService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user/api/v0.0.1/lol/duo")
@RequiredArgsConstructor
public class LolProfileCardController {
    private final LolProfileCardService lolProfileCardService;


    @ApiOperation(value = "프로필 카드 제작", notes = "유저 인증 정보와 프로필 카드 제작 입력값을 이용해 프로필 카드를 제작합니다.\n" +
            "1. 이미 듀오 프로필 카드를 보유하고 있는 유저가 API 호출시 에러가 발생합니다.(프로필카드 중복생성 방지)\n" +
            "2. 이미 프로필 카드에 등록된 소환사명으로 API 호출시 에러가 발생합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "generatedId", value = "회원가입 및 로그인시 제공받은 Id", defaultValue = "sdsnadnsao21n3o1ni3o1"),
            @ApiImplicitParam(name = "clientVersion", value = "클라이언트 애플리케이션 버전", defaultValue = "v1.00"),
            @ApiImplicitParam(name = "voice", value = "보이스 채팅 여부", defaultValue = "true"),
            @ApiImplicitParam(name = "summonerName", value = "소환사명", defaultValue = "세주하리"),
            @ApiImplicitParam(name = "tier", value = "티어 알파벳 대문자 한 글자 (GM은 두 글자)", defaultValue = "GM"),
            @ApiImplicitParam(name = "tierLev", value = "티어 단계 ex) 골드 '3' 티어", defaultValue = "3"),
            @ApiImplicitParam(name = "lp", value = "lp 마, 그마, 챌 아니면 0으로", defaultValue = "300"),
            @ApiImplicitParam(name = "champion1", value = "챔피언 1 - 형태 상관 없음, 챔피언 명은 영어로 보내는 편이 클라이언트에 유리", defaultValue = "LEE SIN"),
            @ApiImplicitParam(name = "champion2", value = "챔피언 2 - 형태 상관 없음, 챔피언 명은 영어로 보내는 편이 클라이언트에 유리", defaultValue = "TWISTED FATE"),
            @ApiImplicitParam(name = "champion3", value = "챔피언 3 - 형태 상관 없음, 챔피언 명은 영어로 보내는 편이 클라이언트에 유리", defaultValue = "GNAR"),
            @ApiImplicitParam(name = "top", value = "탑을 포지션 선택했는지 여부", defaultValue = "true"),
            @ApiImplicitParam(name = "jungle", value = "정글을 포지션 선택했는지 여부", defaultValue = "true"),
            @ApiImplicitParam(name = "mid", value = "미드를 포지션 선택했는지 여부", defaultValue = "true"),
            @ApiImplicitParam(name = "ad", value = "원딜을 포지션 선택했는지 여부", defaultValue = "false"),
            @ApiImplicitParam(name = "support", value = "서포터를 포지션 선택했는지 여부", defaultValue = "false"),
            @ApiImplicitParam(name = "mainPosition", value = "메인 포지션 대문자", defaultValue = "JUNGLE"),
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PostMapping("/my-card")
    public ResponseMessage make(@RequestBody LolProfileCardMakeRequestDto requestDto) {
        lolProfileCardService.makeDuoProfileCard(requestDto);
        return new ResponseMessage("Duo Profile Card Make API Call Success!", "LDPC-OBJ-001");
    }



    @ApiOperation(value = "단일 유저 프로필 카드 데이터 요청", notes = "유저 인증 정보로 프로필카드 데이터 리턴.\n" +
            "1. 프로필 카드가 없을 경우 404 Not Found와 함께 에러 메세지 response 리턴",
            response = LolProfileCardResponseDto.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "generatedId", value = "회원가입 및 로그인시 제공받은 Id", defaultValue = "sdsnadnsao21n3o1ni3o1"),
            @ApiImplicitParam(name = "clientVersion", value = "클라이언트 애플리케이션 버전", defaultValue = "v1.00"),
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping("/my-card")
    public ResponseMessage getDuoProfile(@RequestParam String clientVersion,
                                         @RequestParam String generatedId) {
        LolProfileCardRequestDto requestDto = new LolProfileCardRequestDto();
        requestDto.setClientVersion(clientVersion);
        requestDto.setGeneratedId(generatedId);
        LolProfileCardResponseDto responseDto = lolProfileCardService.take(requestDto);
        return new ResponseMessage("Duo Profile Card Get API Call Success!", "LDPC-OBJ-002", responseDto);
    }

    @ApiOperation(value = "프로필카드 수정",
            notes = "유저 인증 정보와 새로운 프로필카드 데이터로 프로필카드 업데이트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "generatedId", value = "회원가입 및 로그인시 제공받은 Id", defaultValue = "sdsnadnsao21n3o1ni3o1"),
            @ApiImplicitParam(name = "clientVersion", value = "클라이언트 애플리케이션 버전", defaultValue = "v1.00"),
            @ApiImplicitParam(name = "voice", value = "보이스 채팅 여부", defaultValue = "true"),
            @ApiImplicitParam(name = "summonerName", value = "소환사명", defaultValue = "세주하리"),
            @ApiImplicitParam(name = "tier", value = "티어 알파벳 대문자 한 글자 (GM은 두 글자)", defaultValue = "GM"),
            @ApiImplicitParam(name = "tierLev", value = "티어 단계 ex) 골드 '3' 티어", defaultValue = "3"),
            @ApiImplicitParam(name = "lp", value = "lp 마, 그마, 챌 아니면 0으로", defaultValue = "300"),
            @ApiImplicitParam(name = "champion1", value = "챔피언 1 - 형태 상관 없음, 챔피언 명은 영어로 보내는 편이 클라이언트에 유리", defaultValue = "LEE SIN"),
            @ApiImplicitParam(name = "champion2", value = "챔피언 2 - 형태 상관 없음, 챔피언 명은 영어로 보내는 편이 클라이언트에 유리", defaultValue = "TWISTED FATE"),
            @ApiImplicitParam(name = "champion3", value = "챔피언 3 - 형태 상관 없음, 챔피언 명은 영어로 보내는 편이 클라이언트에 유리", defaultValue = "GNAR"),
            @ApiImplicitParam(name = "top", value = "탑을 포지션 선택했는지 여부", defaultValue = "true"),
            @ApiImplicitParam(name = "jungle", value = "정글을 포지션 선택했는지 여부", defaultValue = "true"),
            @ApiImplicitParam(name = "mid", value = "미드를 포지션 선택했는지 여부", defaultValue = "true"),
            @ApiImplicitParam(name = "ad", value = "원딜을 포지션 선택했는지 여부", defaultValue = "false"),
            @ApiImplicitParam(name = "support", value = "서포터를 포지션 선택했는지 여부", defaultValue = "false"),
            @ApiImplicitParam(name = "mainPosition", value = "메인 포지션 대문자", defaultValue = "JUNGLE"),
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PutMapping("/my-card")
    public ResponseMessage updateDuoProfileCard(@RequestBody LolProfileCardUpdateRequestDto requestDto) {
        lolProfileCardService.updateMyCard(requestDto);
        return new ResponseMessage("Duo Profile Card Update API Call Success!", "LDPC-OBJ-003");
    }

    @ApiOperation(value = "매치 기능 프로필 카드 열람",
            notes = "매칭 시작 및 새로고침시 프로필 카드 덱 요청에 따른 덱 반환\n" +
                    "1. 이미 매칭중인 유저가 신청할 시 Bad Request 발생",
            response = LolProcessedProfileCardDeck.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "generatedId", value = "회원가입 및 로그인시 제공받은 Id", defaultValue = "sdsnadnsao21n3o1ni3o1"),
            @ApiImplicitParam(name = "clientVersion", value = "클라이언트 애플리케이션 버전", defaultValue = "v1.00"),
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping("/match-deck")
    public ResponseMessage getMatchingDeck(@RequestParam String clientVersion,
                                           @RequestParam String generatedId) {
        LolMatchDeckRequestDto requestDto = new LolMatchDeckRequestDto();
        requestDto.setClientVersion(clientVersion);
        requestDto.setGeneratedId(generatedId);
        LolProcessedProfileCardDeck deck = lolProfileCardService.takeDeck(requestDto);
        return new ResponseMessage("Lol duo profile card deck return API Call Success!", "LDPC-OBJ-004", deck);
    }
}
