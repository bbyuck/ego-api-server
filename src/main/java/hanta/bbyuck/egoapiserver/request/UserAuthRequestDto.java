package hanta.bbyuck.egoapiserver.request;

import hanta.bbyuck.egoapiserver.domain.SnsVendor;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserAuthRequestDto {
    @ApiModelProperty(name = "snsVendor", example = "NAVER")
    public SnsVendor snsVendor;
    @ApiModelProperty(name = "snsId", example = "214141412421412441")
    public String snsId;
    @ApiModelProperty(name = "email", example = "k941026h@naver.com")
    public String email;
    @ApiModelProperty(name = "clientVersion", example = "v1.99")
    public String clientVersion;

}
