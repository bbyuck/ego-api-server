package hanta.bbyuck.egoapiserver.response.lol;

import hanta.bbyuck.egoapiserver.domain.lol.enumset.LolMatchingStatus;
import hanta.bbyuck.egoapiserver.domain.lol.enumset.LolPosition;
import hanta.bbyuck.egoapiserver.domain.lol.enumset.LolTier;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LolMatchingResponseDto {
    private Long matchId;
    private LocalDateTime matchStartTime;

    private LolMatchingStatus matchingStatus;

    private Boolean opponentVoice;
    private String opponentSummonerName;
    private LolTier opponentTier;
    private Integer opponentTierLev;
    private Integer opponentLp;
    private String opponentChampion1;
    private String opponentChampion2;
    private String opponentChampion3;
    private Boolean opponentTop;
    private Boolean opponentJungle;
    private Boolean opponentMid;
    private Boolean opponentAd;
    private Boolean opponentSupport;
    private LolPosition opponentMainLolPosition;
    private LocalDateTime opponentLastActiveTime;
}
