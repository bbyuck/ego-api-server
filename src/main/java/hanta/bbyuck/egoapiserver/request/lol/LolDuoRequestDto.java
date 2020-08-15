package hanta.bbyuck.egoapiserver.request.lol;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LolDuoRequestDto {
    @ApiModelProperty(name = "sendAuth", example = "dsaodakp1212141242oinoi3n42")
    private String senderAuth;

    @ApiModelProperty(name = "receiverProfileCardId", example = "12")
    private Long receiverProfileCardId;
}
