package hanta.bbyuck.egoapiserver.response.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DuoProfileCardMakeResponse {

    @ApiModelProperty(example = "1421442")
    private Long profileCardId;
}
