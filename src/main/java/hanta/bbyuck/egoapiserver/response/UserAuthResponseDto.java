package hanta.bbyuck.egoapiserver.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class UserAuthResponseDto {

    @ApiModelProperty(example = "유저 인증 토큰값")
    private String userAuthToken;

    private String generatedId;
    private String generatedPw;
}
