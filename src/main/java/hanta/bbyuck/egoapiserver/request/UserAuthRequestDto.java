package hanta.bbyuck.egoapiserver.request;

import hanta.bbyuck.egoapiserver.domain.enumset.SnsVendor;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/*
 * <pre>
 * Copyright (c) 2020 HANTA
 * All rights reserved.
 *
 * This software is the proprietary information of HANTA
 * </pre>
 *
 * @ author 강혁(bbyuck) (k941026h@naver.com)
 * @ since  2020. 01. 01
 *
 * @History
 * <pre>
 * -----------------------------------------------------
 * 2020.01.01
 * bbyuck (k941026h@naver.com) 최초작성
 * -----------------------------------------------------
 * </pre>
 */

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
