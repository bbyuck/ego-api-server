package hanta.bbyuck.egoapiserver.response.lol;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class LolDuoProfileCardDeck {
    // 카드덱이 만들어진 시간
    LocalDateTime makeTime;
    Integer cardCount;
    List<ProcessedLolDuoProfileCard> duoProfileCards;
}
