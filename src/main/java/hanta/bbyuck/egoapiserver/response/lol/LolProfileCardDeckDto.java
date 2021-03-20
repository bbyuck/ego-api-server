package hanta.bbyuck.egoapiserver.response.lol;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

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
public class LolProfileCardDeckDto {
    // 카드덱이 만들어진 시간
    @ApiModelProperty(name = "카드덱이 만들어진 시간",example = "2020-08-14T20:30:08.604")
    private LocalDateTime makeTime;

    @ApiModelProperty(name = "카드덱에 포함된 카드 수", example = "50")
    private Integer cardCount;

    @ApiModelProperty(name = "가공처리된 카드덱 리스트 -> 카드 객체 list")
    private List<LolProfileCardResponseDto> duoProfileCards;

}
