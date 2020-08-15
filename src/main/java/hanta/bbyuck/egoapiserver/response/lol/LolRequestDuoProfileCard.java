package hanta.bbyuck.egoapiserver.response.lol;

import hanta.bbyuck.egoapiserver.domain.lol.LolPosition;
import hanta.bbyuck.egoapiserver.domain.lol.LolTier;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LolRequestDuoProfileCard {
    private Long profileCardId;
    private Boolean voice;
    private LocalDateTime requestTime;
    private String limitedSummonerName;
    private LolTier tier;
    private Integer tierLev;
    private Integer lp;
    private String champion1;
    private String champion2;
    private String champion3;
    private Boolean top;
    private Boolean jungle;
    private Boolean mid;
    private Boolean ad;
    private Boolean support;
    private LolPosition mainLolPosition;
}