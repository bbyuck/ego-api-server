package hanta.bbyuck.egoapiserver.request;

import hanta.bbyuck.egoapiserver.domain.MatchingStatus;
import hanta.bbyuck.egoapiserver.domain.SnsVendor;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserAuthRequest {
    @ApiModelProperty(example = "NAVER")
    public SnsVendor snsVendor;
    @ApiModelProperty(example = "214141412421412441")
    public String snsId;
    @ApiModelProperty(example = "k941026h@naver.com")
    public String email;
}
