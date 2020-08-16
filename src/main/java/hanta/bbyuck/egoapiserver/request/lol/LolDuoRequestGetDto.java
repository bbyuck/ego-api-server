package hanta.bbyuck.egoapiserver.request.lol;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LolDuoRequestGetDto {
    @ApiModelProperty(example = "v1.00")
    public String clientVersion;

    @ApiModelProperty(name = "userAuth", example = "21313dnsiadno123in432")
    private String userAuth;
}