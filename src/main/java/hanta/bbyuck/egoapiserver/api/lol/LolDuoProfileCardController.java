package hanta.bbyuck.egoapiserver.api.lol;

import hanta.bbyuck.egoapiserver.request.lol.LolDuoMatchDeckRequestDto;
import hanta.bbyuck.egoapiserver.request.lol.LolDuoProfileCardRequestDto;
import hanta.bbyuck.egoapiserver.request.lol.LolDuoProfileCardMakeRequestDto;
import hanta.bbyuck.egoapiserver.request.lol.LolDuoProfileCardUpdateRequestDto;
import hanta.bbyuck.egoapiserver.response.lol.LolDuoProfileCardDeck;
import hanta.bbyuck.egoapiserver.response.ResponseMessage;
import hanta.bbyuck.egoapiserver.response.lol.LolDuoProfileCardResponseDto;
import hanta.bbyuck.egoapiserver.service.lol.LolDuoProfileCardService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LolDuoProfileCardController {
    private final LolDuoProfileCardService lolDuoProfileCardService;


    @ApiOperation(value = "프로필 카드 제작", notes = "유저 인증 정보와 프로필 카드 제작 입력값을 이용해 프로필 카드를 제작합니다.\n" +
            "1. 이미 듀오 프로필 카드를 보유하고 있는 유저가 API 호출시 에러가 발생합니다.(프로필카드 중복생성 방지)\n" +
            "2. 이미 프로필 카드에 등록된 소환사명으로 API 호출시 에러가 발생합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ownerAuth", value = "회원가입시 할당받은 유저 인증값", defaultValue = "73bHwJ0Tw12KbrhDDyqJSUMgCVol5bfcLW+fZxBfPkY="),
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
            @ApiImplicitParam(name = "mainPosition", value = "메인 포지션 대문자", defaultValue = "JUNGLE")
    })
    @PostMapping("/api/v0.0.1/lol/duo/my-card")
    public ResponseMessage make(@RequestBody LolDuoProfileCardMakeRequestDto requestDto) {
        lolDuoProfileCardService.makeDuoProfileCard(requestDto);
        return new ResponseMessage("Duo Profile Card Make API Call Success!");
    }


    @ApiOperation(value = "프로필카드 데이터 요청", notes = "유저 인증 정보로 프로필카드 데이터 리턴.\n" +
            "1. 프로필카드가 없을 경우 404 Not Found와 함께 에러 메세지 response 리턴")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ownerAuth", value = "인증 정보", defaultValue = "dwqdqweqe123213214")
    })
    @GetMapping("/api/v0.0.1/lol/duo/my-card")
    public ResponseMessage getDuoProfile(@RequestBody LolDuoProfileCardRequestDto requestDto) {
        LolDuoProfileCardResponseDto responseDto = lolDuoProfileCardService.take(requestDto);
        return new ResponseMessage("Duo Profile Card Get API Call Success!", responseDto);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "ownerAuth", value = "회원가입시 할당받은 유저 인증값", defaultValue = "73bHwJ0Tw12KbrhDDyqJSUMgCVol5bfcLW+fZxBfPkY="),
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
            @ApiImplicitParam(name = "mainPosition", value = "메인 포지션 대문자", defaultValue = "JUNGLE")
    })
    @ApiOperation(value = "프로필카드 수정", notes = "유저 인증 정보와 새로운 프로필카드 데이터로 프로필카드 업데이트")
    @PutMapping("/api/v0.0.1/lol/duo/my-card")
    public ResponseMessage updateDuoProfileCard(@RequestBody LolDuoProfileCardUpdateRequestDto requestDto) {
        lolDuoProfileCardService.updateMyCard(requestDto);
        return new ResponseMessage("Duo Profile Card Update API Call Success!");
    }

    @GetMapping("/api/v0.0.1/lol/duo/match-deck")
    public ResponseMessage getMachingDeck(@RequestBody LolDuoMatchDeckRequestDto requestDto) {
        LolDuoProfileCardDeck deck = lolDuoProfileCardService.takeDeck(requestDto);
        return new ResponseMessage("Lol duo profile card deck return API Call Success!", deck);
    }
}
