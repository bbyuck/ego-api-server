package hanta.bbyuck.egoapiserver.api;

import hanta.bbyuck.egoapiserver.request.DuoProfileCardMakeRequest;
import hanta.bbyuck.egoapiserver.response.DuoProfileCardMakeResponse;
import hanta.bbyuck.egoapiserver.service.DuoProfileCardService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DuoProfileCardController {
    private final DuoProfileCardService duoProfileCardService;


    @ApiOperation(value = "프로필카드 만들기", notes = "유저 인증 정보와 프로필 카드 제작 입력값을 이용해 프로필 카드를 제작합니다.")
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
    @ApiResponses({
            @ApiResponse(code = 200, message = "프로필 만들기 성공")
    })
    @PostMapping("/api/v0.0.1/duo-profile-card")
    public DuoProfileCardMakeResponse make(@RequestBody DuoProfileCardMakeRequest request){
        Long profileCardId = duoProfileCardService.makeDuoProfileCard(request);
        DuoProfileCardMakeResponse response = new DuoProfileCardMakeResponse();

        response.setProfileCardId(profileCardId);

        if (profileCardId == -1L) {
            response.setDuoProfileCardMakeSuccess(false);
        } else {
            response.setDuoProfileCardMakeSuccess(true);
        }

        return response;
    }
}
