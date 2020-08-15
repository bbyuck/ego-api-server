package hanta.bbyuck.egoapiserver.request.lol;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LolDuoProfileCardRequestDto {
    @ApiModelProperty(name = "유저 인증 정보   ::  localStorage 저장값", example = "73bHwJ0Tw12KbrhDDyqJSUMgCVol5bfcLW+fZxBfPkY=")
    String ownerAuth;
}
