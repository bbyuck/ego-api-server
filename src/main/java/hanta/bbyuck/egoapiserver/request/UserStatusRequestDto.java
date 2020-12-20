package hanta.bbyuck.egoapiserver.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserStatusRequestDto {
    @ApiModelProperty(name = "clientVersion", example = "v1.99")
    private String clientVersion;

    @ApiModelProperty(name = "유저 인증 정보  ::  localStorage 저장값", example = "73bHwJ0Tw12KbrhDDyqJSUMgCVol5bfcLW+fZxBfPkY=")
    private String generatedId;
}
