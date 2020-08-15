package hanta.bbyuck.egoapiserver.response.lol;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.domain.lol.LolPosition;
import hanta.bbyuck.egoapiserver.domain.lol.LolTier;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
public class LolProcessedDuoProfileCard {
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
}
