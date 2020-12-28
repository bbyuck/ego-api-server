package hanta.bbyuck.egoapiserver.request.lol;

import hanta.bbyuck.egoapiserver.domain.enumset.RequestType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LolRequestDto {
    @ApiModelProperty(example = "v1.00")
    public String clientVersion;

    @ApiModelProperty(name = "generatedId", example = "dsaodakp1212141242oinoi3n42")
    private String generatedId;

    // 추천을 받아 요청한 것인지 기본 요청인지
    private RequestType requestType;

    @ApiModelProperty(name = "opponentProfileId", example = "12")
    private Long opponentProfileId;
}
