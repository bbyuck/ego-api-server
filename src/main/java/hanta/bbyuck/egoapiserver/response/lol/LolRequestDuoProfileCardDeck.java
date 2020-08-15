package hanta.bbyuck.egoapiserver.response.lol;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class LolRequestDuoProfileCardDeck {
    // 카드덱이 만들어진 시간
    @ApiModelProperty(name = "카드덱이 만들어진 시간",example = "2020-08-14T20:30:08.604")
    LocalDateTime makeTime;

    @ApiModelProperty(name = "카드덱에 포함된 카드 수", example = "50")
    Integer cardCount;

    List<LolRequestDuoProfileCard> duoRequestProfileCards;

}
