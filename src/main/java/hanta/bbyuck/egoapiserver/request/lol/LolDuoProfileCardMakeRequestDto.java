package hanta.bbyuck.egoapiserver.request.lol;

import hanta.bbyuck.egoapiserver.domain.lol.enumset.LolPosition;
import hanta.bbyuck.egoapiserver.domain.lol.enumset.LolTier;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LolDuoProfileCardMakeRequestDto {
    @ApiModelProperty(example = "v1.00")
    private String clientVersion;

    @ApiModelProperty(name = "유저 인증 정보  ::  localStorage 저장값", example = "73bHwJ0Tw12KbrhDDyqJSUMgCVol5bfcLW+fZxBfPkY=")
    private String generatedId;

    @ApiModelProperty(name = "보이스 여부", example = "true")
    private Boolean voice;

    @ApiModelProperty(name = "소환사명", example = "세주하리")
    private String summonerName;

    @ApiModelProperty(name = "티어", example = "GM")
    private LolTier tier;

    @ApiModelProperty(name = "티어 단계", example = "1")
    private Integer tierLev;

    @ApiModelProperty(name = "LP", example = "325")
    private Integer lp;

    @ApiModelProperty(name = "챔피언 1", example = "LEE SIN")
    private String champion1;

    @ApiModelProperty(name = "챔피언 2", example = "TWISTED FATE")
    private String champion2;

    @ApiModelProperty(name = "챔피언 3", example = "GNAR")
    private String champion3;

    @ApiModelProperty(name = "탑 포지션 가능 여부", example = "true")
    private Boolean top;

    @ApiModelProperty(name = "정글 포지션 가능 여부", example = "true")
    private Boolean jungle;

    @ApiModelProperty(name = "미드 포지션 가능 여부", example = "true")
    private Boolean mid;

    @ApiModelProperty(name = "원딜 포지션 가능 여부", example = "false")
    private Boolean ad;

    @ApiModelProperty(name = "서포터 포지션 가능 여부", example = "false")
    private Boolean support;

    @ApiModelProperty(name = "메인 포지션", example = "JUNGLE")
    private LolPosition mainLolPosition;
}
