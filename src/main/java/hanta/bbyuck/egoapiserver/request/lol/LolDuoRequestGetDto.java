package hanta.bbyuck.egoapiserver.request.lol;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LolDuoRequestGetDto {
    @ApiModelProperty(name = "userAuth", example = "21313dnsiadno123in432")
    private String userAuth;
}