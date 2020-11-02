package hanta.bbyuck.egoapiserver.request;

import hanta.bbyuck.egoapiserver.domain.enumset.Game;
import hanta.bbyuck.egoapiserver.domain.enumset.GameType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EgoScoreRequestDto {
    @ApiModelProperty(example = "v1.00")
    private String clientVersion;

    private Game game;
    private GameType gameType;
    private String generatedId;
    private Boolean good;
    private Long opponentProfileCardId;
}
