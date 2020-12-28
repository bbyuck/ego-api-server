package hanta.bbyuck.egoapiserver.dto;

import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.domain.lol.enumset.LolPosition;
import hanta.bbyuck.egoapiserver.domain.lol.enumset.LolTier;
import lombok.Data;

@Data
public class ReferralConditions {
    private User apiCaller;
    // 가능한 티어대
    private LolTier[] tiers;
    // 유저에 맞는 포지션
    private LolPosition[] positions;
}