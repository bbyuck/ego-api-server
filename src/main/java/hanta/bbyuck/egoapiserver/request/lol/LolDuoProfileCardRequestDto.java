package hanta.bbyuck.egoapiserver.request.lol;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LolDuoProfileCardRequestDto {

    @ApiModelProperty(example = "v1.00")
    public String clientVersion;

    @ApiModelProperty(name = "유저 인증 정보   ::  localStorage 저장값", example = "73bHwJ0Tw12KbrhDDyqJSUMgCVol5bfcLW+fZxBfPkY=")
    public String generatedId;
}
