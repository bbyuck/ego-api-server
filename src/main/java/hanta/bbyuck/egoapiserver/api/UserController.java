package hanta.bbyuck.egoapiserver.api;

import hanta.bbyuck.egoapiserver.request.UserAuthRequest;
import hanta.bbyuck.egoapiserver.response.UserAuthResponse;
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

    @ApiOperation(value = "로그인", notes = "SNS 인증을 통한 회원가입 및 로그인을 수행합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "snsVendor", value = "SNS 제공자(ex: KAKAO, NAVER)", defaultValue = "KAKAO"),
            @ApiImplicitParam(name = "snsId", value = "SNS 벤더로부터 제공받은 유저 id", defaultValue = "1234567890"),
            @ApiImplicitParam(name = "email", value = "SNS 벤더로부터 제공받은 유저 email", defaultValue = "kanghyuk@gmail.com")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "로그인 성공")
    })
    @GetMapping("/api/v0.0.1/user/auth")
    public UserAuthResponse login(@RequestBody UserAuthRequest request) {
        Long loginId = userService.login(request);

        String returnId = String.format("%010d", loginId);

        UserAuthResponse userAuthResponse = new UserAuthResponse();
        userAuthResponse.setId(returnId);

        return userAuthResponse;
    }


}
