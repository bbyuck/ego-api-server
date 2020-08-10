package hanta.bbyuck.egoapiserver.request;

import hanta.bbyuck.egoapiserver.domain.Position;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DuoProfileCardMakeRequest {
    @ApiModelProperty(example = "73bHwJ0Tw12KbrhDDyqJSUMgCVol5bfcLW+fZxBfPkY=  ::  localStorage 저장값")
    private String ownerAuth;

    @ApiModelProperty(example = "true")
    private Boolean voice;

    @ApiModelProperty(example = "세주하리")
    private String summonerName;

    @ApiModelProperty(example = "GM")
    private String tier;

    @ApiModelProperty(example = "1")
    private Integer tierLev;

    @ApiModelProperty(example = "325")
    private Integer lp;

    @ApiModelProperty(example = "LEE SIN")
    private String champion1;

    @ApiModelProperty(example = "TWISTED FATE")
    private String champion2;

    @ApiModelProperty(example = "GNAR")
    private String champion3;

    @ApiModelProperty(example = "true")
    private Boolean top;

    @ApiModelProperty(example = "true")
    private Boolean jungle;

    @ApiModelProperty(example = "true")
    private Boolean mid;

    @ApiModelProperty(example = "false")
    private Boolean ad;

    @ApiModelProperty(example = "false")
    private Boolean support;

    @ApiModelProperty(example = "JUNGLE")
    private Position mainPosition;
}
