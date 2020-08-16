package hanta.bbyuck.egoapiserver.request.lol;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LolDuoRequestDto {
    @ApiModelProperty(name = "userAuth", example = "dsaodakp1212141242oinoi3n42")
    private String userAuth;

    @ApiModelProperty(name = "opponentProfileCardId", example = "12")
    private Long opponentProfileCardId;
}
