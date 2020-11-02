package hanta.bbyuck.egoapiserver.request;

import hanta.bbyuck.egoapiserver.domain.enumset.Game;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserGameSelectDto {
    @ApiModelProperty(name = "유저 인증 정보  ::  localStorage 저장값", example = "73bHwJ0Tw12KbrhDDyqJSUMgCVol5bfcLW+fZxBfPkY=")
    private String generatedId;

    @ApiModelProperty(name = "clientVersion", example = "v1.99")
    private String clientVersion;

    @ApiModelProperty(name = "game", example = "LOL")
    private Game game;


}
