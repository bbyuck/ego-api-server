package hanta.bbyuck.egoapiserver.request;

import hanta.bbyuck.egoapiserver.domain.enumset.EgoTestVersion;
import hanta.bbyuck.egoapiserver.domain.enumset.Game;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EgoTestAnswerRequestDto {
    @ApiModelProperty(name = "clientVersion", example = "v1.99")
    private String clientVersion;
    @ApiModelProperty(name = "유저 인증 정보  ::  localStorage 저장값", example = "73bHwJ0Tw12KbrhDDyqJSUMgCVol5bfcLW+fZxBfPkY=")
    private String generatedId;
    @ApiModelProperty(name = "game", example = "LOL")
    private Game game;
    private EgoTestVersion egoTestVersion;

    private Boolean answer_1;
    private Boolean answer_2;
    private Boolean answer_3;
    private Boolean answer_4;
    private Boolean answer_5;
    private Boolean answer_6;
    private Boolean answer_7;
    private Boolean answer_8;
    private Boolean answer_9;
    private Boolean answer_10;
    private Boolean answer_11;
    private Boolean answer_12;
    private Boolean answer_13;
}
