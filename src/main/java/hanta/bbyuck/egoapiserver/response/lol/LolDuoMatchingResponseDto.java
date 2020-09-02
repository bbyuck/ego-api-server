package hanta.bbyuck.egoapiserver.response.lol;

import hanta.bbyuck.egoapiserver.domain.lol.enumset.LolPosition;
import hanta.bbyuck.egoapiserver.domain.lol.enumset.LolTier;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LolDuoMatchingResponseDto {
    LocalDateTime matchStartTime;

    private Boolean myVoice;
    private String mySummonerName;
    private LolTier myTier;
    private Integer myTierLev;
    private Integer myLp;
    private String myChampion1;
    private String myChampion2;
    private String myChampion3;
    private Boolean myTop;
    private Boolean myJungle;
    private Boolean myMid;
    private Boolean myAd;
    private Boolean mySupport;
    private LolPosition myMainLolPosition;

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
}
