package hanta.bbyuck.egoapiserver.request.lol;

import hanta.bbyuck.egoapiserver.domain.enumset.MatchType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LolMatchingRequestDto {
    @ApiModelProperty(example = "v1.00")
    private String clientVersion;

    @ApiModelProperty(name = "generatedId", example = "dsaodakp1212141242oinoi3n42")
    private String generatedId;

    @ApiModelProperty(name = "opponentProfileId", example = "12")
    private Long opponentProfileId;

    @ApiModelProperty(name = "matchingId", example = "1223141241")
    private Long matchId;

    @ApiModelProperty(name = "matchType", example = "DUO")
    private MatchType matchType;
}
