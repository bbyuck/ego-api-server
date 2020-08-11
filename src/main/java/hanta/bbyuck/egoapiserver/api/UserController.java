package hanta.bbyuck.egoapiserver.api;

import hanta.bbyuck.egoapiserver.request.UserAuthRequest;
import hanta.bbyuck.egoapiserver.response.ResponseMessage;
import hanta.bbyuck.egoapiserver.response.dto.UserAuthResponse;
import hanta.bbyuck.egoapiserver.service.UserService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @ApiOperation(value = "로그인", notes = "SNS 인증을 통한 회원가입 및 로그인을 수행합니다.\n" +
            "1. SNS 제공자 대문자로 반드시 맞추어서 입력할 것 \n" +
            "2. SNS별로 중복 가입 가능 ex) 1명의 유저가 카카오로 한 번, 네이버로 한 번 가입 가능\n" +
            "3. 기존 회원이면 자동으로 로그인, 회원 정보에 존재하지 않는다면 회원가입 후 자동으로 로그인\n" +
            "4. 자동 로그인 기능은 클라이언트에서 관리")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "snsVendor", value = "SNS 제공자(ex: KAKAO, NAVER)", defaultValue = "KAKAO"),
            @ApiImplicitParam(name = "snsId", value = "SNS 벤더로부터 제공받은 유저 id", defaultValue = "1234567890"),
            @ApiImplicitParam(name = "email", value = "SNS 벤더로부터 제공받은 유저 email", defaultValue = "kanghyuk@gmail.com")
    })
    @GetMapping("/api/v0.0.1/user/auth")
    public ResponseMessage loginTest(@RequestBody UserAuthRequest request) {
        String returnAuth = userService.login(request);
        UserAuthResponse userAuthResponse = new UserAuthResponse();
        userAuthResponse.setUserAuth(returnAuth);

        return new ResponseMessage("Auth API call success", returnAuth);
    }

}
