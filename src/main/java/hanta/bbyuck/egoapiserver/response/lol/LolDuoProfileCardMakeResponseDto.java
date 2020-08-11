package hanta.bbyuck.egoapiserver.response.lol;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LolDuoProfileCardMakeResponseDto {

    @ApiModelProperty(example = "1421442")
    private Long profileCardId;
}
