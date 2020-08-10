package hanta.bbyuck.egoapiserver.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DuoProfileCardMakeResponse {

    @ApiModelProperty(example = "1421442")
    private Long profileCardId;

    @ApiModelProperty(example = "true")
    private Boolean duoProfileCardMakeSuccess;
}
