package hanta.bbyuck.egoapiserver.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserJoinResponseDto {
    @ApiModelProperty(example = "73bHwJ0Tw12KbrhDDyqJSUMgCVol5bfcLW+fZxBfPkY= :: 유저별 인증키값(44 byte), localStorage에 저장")
    private String generatedId;
}
