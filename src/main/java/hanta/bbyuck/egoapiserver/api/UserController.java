package hanta.bbyuck.egoapiserver.api;

import hanta.bbyuck.egoapiserver.domain.enumset.Game;
import hanta.bbyuck.egoapiserver.request.*;
import hanta.bbyuck.egoapiserver.response.*;
import hanta.bbyuck.egoapiserver.security.JwtTokenProvider;
import hanta.bbyuck.egoapiserver.service.EgoScoreService;
import hanta.bbyuck.egoapiserver.service.EgoTestService;
import hanta.bbyuck.egoapiserver.service.UserService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
        String token = jwtTokenProvider.createToken(loginDto.getGeneratedId(), loginDto.getPrivileges());
        loginDto.setUserAuthToken(token);
        return new ResponseMessage("Auth API call success", "AUTH-OBJ-001" ,loginDto);
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
    public ResponseMessage findLastGame(@RequestParam String clientVersion,
                                        @RequestParam String generatedId,
                                        @RequestParam Game game){
        UserGameSelectDto requestDto = new UserGameSelectDto();
        requestDto.setClientVersion(clientVersion);
        requestDto.setGeneratedId(generatedId);
        requestDto.setGame(game);
        UserInfoResponseDto responseDto = userService.findLastPlayGame(requestDto);
        if (responseDto.getGame() == null) return new ResponseMessage("마지막으로 게임을 한 기록이 없음");
        else return new ResponseMessage("마지막으로 한 게임", "GAME-OBJ-001" ,responseDto);
    }

    @GetMapping("/user/api/v0.0.1/status")
    public ResponseMessage myStatus(@RequestParam String clientVersion,
                                    @RequestParam String generatedId) {
        UserStatusRequestDto requestDto = new UserStatusRequestDto();
        requestDto.setClientVersion(clientVersion);
        requestDto.setGeneratedId(generatedId);
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
        EgoTestResponseDto responseDto = null;
        String returnCode = null;
        if (requestDto.getGeneratedId() == null) {
            // 유저 정보 변경 없이 테스트만 수행 -> 웹버전 회원가입유도 필요
            responseDto = egoTestService.saveAnswer(requestDto);
            returnCode = "EGO-OBJ-002";
        }
        else {
            // 유저 정보 포함 테스트 수행 -> 앱버전
            responseDto = egoTestService.saveAnswer(requestDto);
            returnCode = "EGO-OBJ-001";
        }

        return new ResponseMessage("테스트 완료", returnCode, responseDto);

    }
}
