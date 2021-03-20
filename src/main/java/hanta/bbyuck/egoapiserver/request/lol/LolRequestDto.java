package hanta.bbyuck.egoapiserver.request.lol;

import hanta.bbyuck.egoapiserver.domain.enumset.RequestType;
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
public class LolRequestDto {
    @ApiModelProperty(example = "v1.00")
    public String clientVersion;

    @ApiModelProperty(name = "generatedId", example = "dsaodakp1212141242oinoi3n42")
    private String generatedId;

    // 추천을 받아 요청한 것인지 기본 요청인지
    private RequestType requestType;

    @ApiModelProperty(name = "opponentProfileId", example = "12")
    private Long opponentProfileId;
}
