package hanta.bbyuck.egoapiserver.request.lol;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LolRequestGetDto {
    @ApiModelProperty(example = "v1.00")
    private String clientVersion;

    @ApiModelProperty(name = "generatedId", example = "21313dnsiadno123in432")
    private String generatedId;
}