package hanta.bbyuck.egoapiserver.request;

import hanta.bbyuck.egoapiserver.domain.enumset.SnsVendor;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserAuthRequestDto {
    @ApiModelProperty(name = "snsVendor", example = "NAVER")
    private SnsVendor snsVendor;
    @ApiModelProperty(name = "snsId", example = "214141412421412441")
    private String snsId;
    @ApiModelProperty(name = "email", example = "k941026h@naver.com")
    private String email;
    @ApiModelProperty(name = "clientVersion", example = "v1.99")
    private String clientVersion;

    @ApiModelProperty(name = "fcmToken", example = "dsa2dd9dsjdoandoisnoidqw")
    private String fcmToken;

}
