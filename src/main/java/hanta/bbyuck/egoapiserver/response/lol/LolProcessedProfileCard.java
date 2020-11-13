package hanta.bbyuck.egoapiserver.response.lol;

import hanta.bbyuck.egoapiserver.domain.enumset.EgoTestVersion;
import hanta.bbyuck.egoapiserver.domain.enumset.MatchType;
import hanta.bbyuck.egoapiserver.domain.lol.enumset.LolPosition;
import hanta.bbyuck.egoapiserver.domain.lol.enumset.LolTier;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LolProcessedProfileCard {
    private Long profileCardId;
    private Boolean voice;
    private LocalDateTime lastActiveTime;
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
    private MatchType matchType;
    private String userType;
    private EgoTestVersion egoTestVersion;
}
