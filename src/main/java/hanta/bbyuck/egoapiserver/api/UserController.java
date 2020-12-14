package hanta.bbyuck.egoapiserver.api;

import hanta.bbyuck.egoapiserver.request.*;
import hanta.bbyuck.egoapiserver.response.*;
import hanta.bbyuck.egoapiserver.security.JwtTokenProvider;
import hanta.bbyuck.egoapiserver.service.EgoScoreService;
import hanta.bbyuck.egoapiserver.service.EgoTestService;
import hanta.bbyuck.egoapiserver.service.UserService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final EgoTestService egoTestService;
    private final EgoScoreService egoScoreService;

    @ApiOperation(value = "회원가입", notes = "SNS 인증을 통한 회원가입을 수행합니다.\n" +
            "1. SNS 제공자 대문자로 반드시 맞추어서 입력할 것 \n" +
            "2. SNS별로 중복 가입 가능 ex) 1명의 유저가 카카오로 한 번, 네이버로 한 번 가입 가능 -> 클라이언트에서 UX 설계로 해결\n" +
            "3. 기존 회원이면 자동으로 로그인, 회원 정보에 존재하지 않는다면 회원가입 후 자동으로 로그인\n" +
            "4. 자동 로그인 기능은 클라이언트에서 관리")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "snsVendor", value = "SNS 제공자(ex: KAKAO, NAVER)", defaultValue = "KAKAO"),
            @ApiImplicitParam(name = "snsId", value = "SNS 벤더로부터 제공받은 유저 id", defaultValue = "1234567890"),
            @ApiImplicitParam(name = "email", value = "SNS 벤더로부터 제공받은 유저 email", defaultValue = "kanghyuk@gmail.com"),
            @ApiImplicitParam(name = "clientVersion", value = "클라이언트 애플리케이션 버전", defaultValue = "v1.00")
    })
    @PostMapping("/api/v0.0.1/auth")
    public ResponseMessage join(@RequestBody UserAuthRequestDto requestDto) {
        LoginDto loginDto = userService.join(requestDto);


        String token = jwtTokenProvider.createToken(loginDto.getId(), loginDto.getPrivileges());
        UserAuthResponseDto userAuthResponseDto = new UserAuthResponseDto();
        userAuthResponseDto.setUserAuthToken(token);

        userAuthResponseDto.setGeneratedId(loginDto.getGeneratedId());
        userAuthResponseDto.setGeneratedPw(loginDto.getGeneratedPw());

        return new ResponseMessage("Auth API call success", "AUTH-OBJ-001" ,userAuthResponseDto);
    }


    @PutMapping("/user/api/v0.0.1/fcm-auth")
    public ResponseMessage updateFcmAuth(@RequestBody UserAuthRequestDto requestDto) {
        userService.refreshToken(requestDto);
        return new ResponseMessage("FCM AUTH TOKEN UPDATE SUCCESS", "AUTH-NONE-002");
    }

    @PutMapping("/user/api/v0.0.1/game")
    public ResponseMessage selectGame(@RequestBody UserGameSelectDto requestDto) {
        userService.updateGame(requestDto);
        return new ResponseMessage("YOU SIGN IN " + requestDto.getGame().toString(), "GAME-NONE-001");
    }

    @GetMapping("/user/api/v0.0.1/game")
    public ResponseMessage findLastGame(@RequestBody UserGameSelectDto requestDto){
        UserInfoResponseDto responseDto = userService.findLastPlayGame(requestDto);
        if (responseDto.getGame() == null) return new ResponseMessage("마지막으로 게임을 한 기록이 없음");
        else return new ResponseMessage("마지막으로 한 게임", "GAME-OBJ-001" ,responseDto);
    }

    @GetMapping("/user/api/v0.0.1/status")
    public ResponseMessage myStatus(@RequestBody UserStatusRequestDto requestDto) {
        UserStatusResponseDto responseDto = userService.findStatus(requestDto);
        return new ResponseMessage("유저 상태 리턴 및 최근 활동시각 업데이트", "USER-OBJ-102", responseDto);
    }

    @PostMapping("/user/api/v0.0.1/match/evaluation")
    public ResponseMessage evaluateOpponent(@RequestBody EgoScoreRequestDto requestDto) {
        egoScoreService.evaluateOpponent(requestDto);
        return new ResponseMessage("평가 완료", "ESTI-NONE-002");
    }

    // 유저 정보 필요없
    @GetMapping("/api/v0.0.1/user/ego-test")
    public ResponseMessage egoWebTest(@RequestBody EgoTestAnswerRequestDto requestDto) {
        EgoTestResponseDto responseDto = egoTestService.onlyTest(requestDto);
        return new ResponseMessage("테스트 완료, 앱 다운로드 페이지로", "EGO-OBJ-002", responseDto);
    }

    @PostMapping("/user/api/v0.0.1/user/ego-test")
    public ResponseMessage egoAppTest(@RequestBody EgoTestAnswerRequestDto requestDto) {
        EgoTestResponseDto responseDto = egoTestService.saveAnswer(requestDto);
        return new ResponseMessage("테스트 완료", "EGO-OBJ-001", responseDto);
    }
}
