package hanta.bbyuck.egoapiserver.request.lol;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LolRequestDto {
    @ApiModelProperty(example = "v1.00")
    public String clientVersion;

    @ApiModelProperty(name = "generatedId", example = "dsaodakp1212141242oinoi3n42")
    private String generatedId;

    @ApiModelProperty(name = "opponentProfileId", example = "12")
    private Long opponentProfileId;
}
